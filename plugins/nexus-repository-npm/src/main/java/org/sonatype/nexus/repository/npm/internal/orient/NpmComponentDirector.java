package org.sonatype.nexus.repository.npm.internal.orient;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.NpmPackageId;
import org.sonatype.nexus.repository.npm.internal.NpmPackageParser;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentDirector;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.npm.internal.NpmAttributes.P_NAME;
import static org.sonatype.nexus.repository.npm.internal.orient.NpmPackageRootMetadataUtils.createFullPackageMetadata;
import static org.sonatype.nexus.repository.npm.internal.NpmVersionComparator.extractNewestVersion;

/**
 * @since 3.11
 */
@Named("npm")
@Singleton
public class NpmComponentDirector
    extends ComponentSupport
    implements ComponentDirector
{
  private NpmPackageParser npmPackageParser;

  @Inject
  public NpmComponentDirector(final NpmPackageParser npmPackageParser)
  {
    this.npmPackageParser = npmPackageParser;
  }

  @Override
  public boolean allowMoveTo(final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveTo(final Component component, final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveFrom(final Repository source) {
    return true;
  }

  @Override
  public Component afterMove(final Component component, final Repository destination) {
    destination.optionalFacet(OrientNpmHostedFacet.class).ifPresent(npmHostedFacet -> {

      UnitOfWork.begin(destination.facet(StorageFacet.class).txSupplier());
      try {
        updatePackageRoot(npmHostedFacet, component, destination);
      }
      finally {
        UnitOfWork.end();
      }

    });
    return component;
  }

  @Transactional
  protected void updatePackageRoot(final OrientNpmHostedFacet npmHostedFacet,
                                   final Component component,
                                   final Repository destination)
  {
    final StorageTx tx = UnitOfWork.currentTx();
    tx.browseAssets(component).forEach(asset -> {
      Blob blob = checkNotNull(tx.getBlob(asset.blobRef()));
      final Map<String, Object> packageJson = npmPackageParser.parsePackageJson(blob::getInputStream);
      final NpmPackageId packageId = NpmPackageId.parse((String) packageJson.get(P_NAME));

      try {
        final NestedAttributesMap updatedMetadata = createFullPackageMetadata(
            new NestedAttributesMap("metadata", packageJson),
            destination.getName(),
            blob.getMetrics().getSha1Hash(),
            destination,
            extractNewestVersion);
        npmHostedFacet.putPackageRoot(packageId, null, updatedMetadata);
      }
      catch (IOException e) {
        log.error("Failed to update package root, packageId: {}", packageId, e);
      }
    });
  }
}
