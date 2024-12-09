package org.sonatype.repository.helm.internal.content.recipe;

import java.io.InputStream;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.PartPayload;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.rest.ValidationErrorsException;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.internal.AssetKind;
import org.sonatype.repository.helm.internal.content.HelmContentFacet;
import org.sonatype.repository.helm.internal.util.HelmAttributeParser;
import org.sonatype.repository.helm.internal.util.HelmPathUtils;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;
import static org.sonatype.nexus.repository.http.HttpResponses.created;
import static org.sonatype.nexus.repository.http.HttpResponses.notFound;
import static org.sonatype.nexus.repository.http.HttpResponses.ok;

/**
 * Helm Hosted Handlers
 *
 * @since 3.28
 */
@Named
@Singleton
public class HostedHandlers
    extends ComponentSupport
{
  private HelmPathUtils helmPathUtils;

  private HelmAttributeParser helmPackageParser;

  @Inject
  public HostedHandlers(final HelmPathUtils helmPathUtils, final HelmAttributeParser helmPackageParser) {
    this.helmPathUtils = checkNotNull(helmPathUtils);
    this.helmPackageParser = checkNotNull(helmPackageParser);
  }

  public final Handler get = context -> {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    String path;
    if (assetKind == AssetKind.HELM_INDEX) {
      path = "/index.yaml";
    }
    else {
      State state = context.getAttributes().require(State.class);
      path = helmPathUtils.contentFilePath(state, true);
    }
    Content content = context.getRepository().facet(HelmHostedFacet.class).get(path);

    return (content != null) ? ok(content) : notFound();
  };

  public final Handler upload = context -> {
    State state = context.getAttributes().require(State.class);
    String path = helmPathUtils.buildContentAssetPath(state);
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    context.getRepository().facet(HelmHostedFacet.class).upload(path, context.getRequest().getPayload(), assetKind);
    return ok();
  };

  public final Handler push = context -> {
    HelmHostedFacet hostedFacet = context.getRepository().facet(HelmHostedFacet.class);
    HelmContentFacet helmContentFacet = context.getRepository().facet(HelmContentFacet.class);

    PartPayload payload = getPartPayload(context);
    String fileName = payload.getName() != null ? payload.getName() : StringUtils.EMPTY;
    AssetKind assetKind = AssetKind.getAssetKindByFileName(fileName);
    try (TempBlob tempBlob = helmContentFacet.getTempBlob(payload);
         InputStream inputStream = tempBlob.get()) {
      HelmAttributes attributes = helmPackageParser.getAttributes(assetKind, inputStream);
      String path = hostedFacet.getPath(attributes, assetKind);

      hostedFacet.upload(path, tempBlob, attributes, payload, assetKind);
      return created();
    }
  };

  private PartPayload getPartPayload(final Context context) {
    if (!context.getRequest().isMultipart()) {
      throw new ValidationErrorsException("request should be multipart");
    }
    return StreamSupport.stream(requireNonNull(context.getRequest().getMultiparts()).spliterator(), false)
        .filter(partPayload -> "chart".equals(partPayload.getFieldName()))
        .findFirst().orElseThrow(() -> new ValidationErrorsException("chart field required"));
  }

  public final Handler delete = context -> {
    State state = context.getAttributes().require(State.class);
    String path = helmPathUtils.buildContentAssetPath(state);

    boolean deleted = context.getRepository().facet(HelmHostedFacet.class).delete(path);

    return (deleted) ? ok() : notFound();
  };
}
