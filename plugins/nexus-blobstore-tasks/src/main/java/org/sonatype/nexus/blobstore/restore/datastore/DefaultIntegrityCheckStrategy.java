package org.sonatype.nexus.blobstore.restore.datastore;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobAttributes;
import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobMetrics;
import org.sonatype.nexus.blobstore.api.BlobRef;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.logging.task.ProgressLogIntervalHelper;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAssets;

import org.apache.commons.lang.StringUtils;

import static com.google.common.base.Preconditions.checkArgument;
import static org.sonatype.nexus.blobstore.api.BlobAttributesConstants.HEADER_PREFIX;
import static org.sonatype.nexus.blobstore.api.BlobStore.BLOB_NAME_HEADER;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.storage.Asset.CHECKSUM;

/**
 * Default {@link IntegrityCheckStrategy} which checks name and SHA1 checksum
 *
 * @since 3.29
 */
@Named(DefaultIntegrityCheckStrategy.DEFAULT_NAME)
@Singleton
public class DefaultIntegrityCheckStrategy
    extends ComponentSupport
    implements IntegrityCheckStrategy
{
  public static final String NAME_MISMATCH = "Name does not match on asset! Metadata name: '{}', Blob name: '{}'";

  public static final String DEFAULT_NAME = "default";

  static final String BLOB_PROPERTIES_MISSING_FOR_ASSET = "Blob properties missing for asset '{}'.";

  static final String BLOB_DATA_MISSING_FOR_ASSET = "Blob data missing for asset '{}'.";

  static final String BLOB_PROPERTIES_MARKED_AS_DELETED =
      "Blob properties marked as deleted for asset '{}'. Will be removed on next compact.";

  static final String SHA1_MISMATCH = "SHA1 does not match on asset '{}'! Metadata SHA1: '{}', Blob SHA1: '{}'";

  static final String ASSET_SHA1_MISSING = "Asset is missing SHA1 hash code";

  static final String BLOB_NAME_MISSING = "Blob properties is missing name";

  static final String ERROR_ACCESSING_BLOB = "Error accessing blob for asset '{}'";

  static final String ASSET_NAME_MISSING = "Asset is missing name";

  static final String ERROR_PROCESSING_ASSET = "Error processing asset '{}'";

  static final String ERROR_PROCESSING_ASSET_WITH_EX = ERROR_PROCESSING_ASSET + ". {} Exception: {}";

  static final String CANCEL_WARNING = "Cancelling blob integrity check";

  static final String BLOB_METRICS_MISSING_SHA1 = "Blob metrics are missing SHA1 hash code";

  static final String ASSET_INTEGRITY_CHECK_FAILED = "Asset integrity check failed for {}";

  private final int browseBatchSize;

  @Inject
  public DefaultIntegrityCheckStrategy(
      @Named("${nexus.blobstore.restore.integrityCheck.batchSize:-1000}") final int browseBatchSize)
  {
    checkArgument(browseBatchSize > 0);
    this.browseBatchSize = browseBatchSize;
  }

  @Override
  public void check(
      final Repository repository,
      final BlobStore blobStore,
      final BooleanSupplier isCancelled,
      @Nullable final Consumer<Asset> integrityCheckFailedHandler)
  {
    log.info("Checking integrity of assets in repository '{}' with blob store '{}'", repository.getName(),
        blobStore.getBlobStoreConfiguration().getName());

    long processed = 0;
    long failures = 0;

    try (ProgressLogIntervalHelper progressLogger = new ProgressLogIntervalHelper(log, 60)) {
      FluentAssets fluentAssets = repository.facet(ContentFacet.class).assets();
      Continuation<FluentAsset> assets = fluentAssets.browse(browseBatchSize, null);
      while (!assets.isEmpty()) {
        for (FluentAsset asset : assets) {
          if (isCancelled.getAsBoolean()) {
            log.warn(CANCEL_WARNING);
            return;
          }

          log.debug("Checking asset {}", asset.path());
          boolean failed = checkAsset(asset, blobStore);

          if (failed) {
            failures++;
            if (integrityCheckFailedHandler != null) {
              integrityCheckFailedHandler.accept(asset);
            }
          }

          progressLogger
              .info("Elapsed time: {}, processed: {}, failed integrity check: {}", progressLogger.getElapsed(),
                  ++processed, failures);
        }

        assets = fluentAssets.browse(browseBatchSize, assets.nextContinuationToken());
      }
    }
  }

  private boolean checkAsset(final Asset asset, final BlobStore blobStore) {
    try {
      Optional<BlobId> blobId = asset
          .blob()
          .map(AssetBlob::blobRef)
          .map(BlobRef::getBlobId);

      if (!blobId.isPresent()) {
        log.error(ERROR_ACCESSING_BLOB, asset.path());
        return true;
      }

      BlobAttributes blobAttributes = blobStore.getBlobAttributes(blobId.get());

      if (blobAttributes == null) {
        log.error(BLOB_PROPERTIES_MISSING_FOR_ASSET, asset.path());
        return true;
      }
      else if (blobAttributes.isDeleted()) {
        log.warn(BLOB_PROPERTIES_MARKED_AS_DELETED, asset.path());
        return true;
      }
      else if (!blobDataExists(blobStore.get(blobId.get()))) {
        log.error(BLOB_DATA_MISSING_FOR_ASSET, asset.path());
        return true;
      }
      else if (!checkAssetIntegrity(blobAttributes, asset)) {
        log.error(ASSET_INTEGRITY_CHECK_FAILED, asset.path());
        return true;
      }
      else {
        return false;
      }
    }
    catch (IllegalArgumentException e) {
      // thrown by checkAsset inner methods
      log.error(ERROR_PROCESSING_ASSET_WITH_EX, asset.toString(), e.getMessage(), log.isDebugEnabled() ? e : null);
      return true;
    }
    catch (Exception e) {
      log.error(ERROR_PROCESSING_ASSET, asset.toString(), e);
      return true;
    }
  }

  /**
   * Check the asset for integrity. By default checks name and SHA1.
   *
   * @param blobAttributes the {@link BlobAttributes} from the {@link Blob}
   * @param asset          the {@link Asset}
   * @return true if asset integrity is intact, false otherwise
   */
  protected boolean checkAssetIntegrity(final BlobAttributes blobAttributes, final Asset asset) {
    checkArgument(blobAttributes.getProperties() != null, "Blob attributes are missing properties");

    return checkSha1(blobAttributes, asset) && checkName(blobAttributes, asset);
  }

  /**
   * returns true if the checksum matches, false otherwise
   */
  private boolean checkSha1(final BlobAttributes blobAttributes, final Asset asset) {
    String assetSha1 = getAssetSha1(asset);
    String blobSha1 = getBlobSha1(blobAttributes);

    if (!Objects.equals(assetSha1, blobSha1)) {
      log.error(SHA1_MISMATCH, asset.path(), assetSha1, blobSha1);
      return false;
    }

    return true;
  }

  /**
   * Get the SHA1 from the {@link BlobAttributes}
   */
  protected String getBlobSha1(final BlobAttributes blobAttributes) {
    BlobMetrics metrics = blobAttributes.getMetrics();
    checkArgument(metrics != null, "Blob attributes are missing metrics");
    String blobSha1 = metrics.getSha1Hash();
    checkArgument(blobSha1 != null, BLOB_METRICS_MISSING_SHA1);
    return blobSha1;
  }

  /**
   * Get the SHA1 from the {@link Asset}
   */
  protected String getAssetSha1(final Asset asset) {
    return asset.blob()
        .map(assetBlob -> assetBlob.checksums().get(SHA1.name()))
        .orElseThrow(() -> new IllegalArgumentException(ASSET_SHA1_MISSING));
  }

  /**
   * returns true if the name matches, false otherwise
   */
  private boolean checkName(final BlobAttributes blobAttributes, final Asset asset) {
    String blobName = getBlobName(blobAttributes);

    checkArgument(blobName != null, BLOB_NAME_MISSING);
    checkArgument(asset.path() != null, ASSET_NAME_MISSING);

    if (!StringUtils.equals(asset.path(), blobName)) {
      log.error(NAME_MISMATCH, blobName, asset.path());
      return false;
    }

    return true;
  }

  /**
   * returns true if the blobs data is accessible, false otherwise
   */
  protected boolean blobDataExists(final Blob blob) {
    try {
      blob.getInputStream().close();
      return true;
    }
    catch (Exception e) { // NOSONAR
      return false;
    }
  }

  /**
   * Get the name from the {@link BlobAttributes}
   */
  protected String getBlobName(final BlobAttributes blobAttributes) {
    return blobAttributes.getProperties().getProperty(HEADER_PREFIX + BLOB_NAME_HEADER);
  }
}
