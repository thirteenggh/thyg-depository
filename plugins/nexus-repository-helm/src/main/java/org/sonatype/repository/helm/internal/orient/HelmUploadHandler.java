package org.sonatype.repository.helm.internal.orient;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.rest.UploadDefinitionExtension;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.upload.ComponentUpload;
import org.sonatype.nexus.repository.upload.UploadResponse;
import org.sonatype.nexus.repository.view.PartPayload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.transaction.UnitOfWork;
import org.sonatype.repository.helm.HelmUploadHandlerSupport;
import org.sonatype.repository.helm.internal.AssetKind;
import org.sonatype.repository.helm.internal.orient.hosted.HelmHostedFacet;
import org.sonatype.repository.helm.internal.util.HelmAttributeParser;

import org.apache.commons.lang3.StringUtils;

import static org.sonatype.repository.helm.internal.HelmFormat.HASH_ALGORITHMS;
import static org.sonatype.repository.helm.internal.HelmFormat.NAME;

/**
 * Support helm upload for web page
 /**
 * @since 3.28
 */
@Singleton
@Named(NAME)
public class HelmUploadHandler
    extends HelmUploadHandlerSupport
{
  @Inject
  public HelmUploadHandler(
      final ContentPermissionChecker contentPermissionChecker,
      final HelmAttributeParser helmPackageParser,
      @Named("simple") final VariableResolverAdapter variableResolverAdapter,
      final Set<UploadDefinitionExtension> uploadDefinitionExtensions)
  {
    super(contentPermissionChecker, helmPackageParser, variableResolverAdapter, uploadDefinitionExtensions);
  }

  @Override
  public UploadResponse handle(final Repository repository, final ComponentUpload upload) throws IOException {
    HelmHostedFacet facet = repository.facet(HelmHostedFacet.class);
    StorageFacet storageFacet = repository.facet(StorageFacet.class);

    PartPayload payload = upload.getAssetUploads().get(0).getPayload();

    String fileName = payload.getName() != null ? payload.getName() : StringUtils.EMPTY;
    AssetKind assetKind = AssetKind.getAssetKindByFileName(fileName);

    try (TempBlob tempBlob = storageFacet.createTempBlob(payload, HASH_ALGORITHMS)) {
      String path = facet.getPath(tempBlob, assetKind);

      ensurePermitted(repository.getName(), NAME, path, Collections.emptyMap());
      try {
        UnitOfWork.begin(storageFacet.txSupplier());
        Asset asset = facet.upload(path, tempBlob, payload, assetKind);
        return new UploadResponse(asset);
      }
      finally {
        UnitOfWork.end();
      }
    }
  }
}
