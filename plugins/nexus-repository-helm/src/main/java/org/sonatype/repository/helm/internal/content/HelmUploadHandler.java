package org.sonatype.repository.helm.internal.content;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.rest.UploadDefinitionExtension;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.upload.ComponentUpload;
import org.sonatype.nexus.repository.upload.UploadDefinition;
import org.sonatype.nexus.repository.upload.UploadResponse;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.PartPayload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.HelmUploadHandlerSupport;
import org.sonatype.repository.helm.internal.AssetKind;
import org.sonatype.repository.helm.internal.content.recipe.HelmHostedFacet;
import org.sonatype.repository.helm.internal.util.HelmAttributeParser;

import org.apache.commons.lang3.StringUtils;

import static org.sonatype.repository.helm.internal.HelmFormat.NAME;
import static org.sonatype.repository.helm.internal.util.HelmAttributeParser.validateAttributes;

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
    HelmContentFacet helmContentFacet = repository.facet(HelmContentFacet.class);

    PartPayload payload = upload.getAssetUploads().get(0).getPayload();

    String fileName = payload.getName() != null ? payload.getName() : StringUtils.EMPTY;
    AssetKind assetKind = AssetKind.getAssetKindByFileName(fileName);

    try (TempBlob tempBlob = helmContentFacet.getTempBlob(payload);
         InputStream inputStream = tempBlob.get()) {
      HelmAttributes attributes = validateAttributes(helmPackageParser.getAttributes(assetKind, inputStream));
      String path = facet.getPath(attributes, assetKind);

      ensurePermitted(repository.getName(), NAME, path, Collections.emptyMap());
      Content content = facet.upload(path, tempBlob, attributes, payload, assetKind);
      return new UploadResponse(Collections.singletonList(content), Collections.singletonList(path));
    }
  }

  @Override
  public UploadDefinition getDefinition() {
    if (definition == null) {
      definition = getDefinition(NAME, false);
    }
    return definition;
  }

  @Override
  public VariableResolverAdapter getVariableResolverAdapter() {
    return variableResolverAdapter;
  }

  @Override
  public ContentPermissionChecker contentPermissionChecker() {
    return contentPermissionChecker;
  }
}
