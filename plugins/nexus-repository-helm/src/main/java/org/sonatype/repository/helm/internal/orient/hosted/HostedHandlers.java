package org.sonatype.repository.helm.internal.orient.hosted;

import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Handler;
import org.sonatype.nexus.repository.view.PartPayload;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.rest.ValidationErrorsException;
import org.sonatype.repository.helm.internal.AssetKind;
import org.sonatype.repository.helm.internal.util.HelmPathUtils;

import org.apache.commons.lang3.StringUtils;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.requireNonNull;
import static org.sonatype.nexus.repository.http.HttpResponses.created;
import static org.sonatype.nexus.repository.http.HttpResponses.notFound;
import static org.sonatype.nexus.repository.http.HttpResponses.ok;
import static org.sonatype.repository.helm.internal.HelmFormat.HASH_ALGORITHMS;

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

  @Inject
  public HostedHandlers(final HelmPathUtils helmPathUtils) {
    this.helmPathUtils = checkNotNull(helmPathUtils);
  }

  public final Handler get = context -> {
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    String path;
    if (assetKind == AssetKind.HELM_INDEX) {
      path = "index.yaml";
    }
    else {
      State state = context.getAttributes().require(State.class);
      path = helmPathUtils.filename(state);
    }
    Content content = context.getRepository().facet(HelmHostedFacet.class).get(path);

    return (content != null) ? ok(content) : notFound();
  };

  public final Handler upload = context -> {
    State state = context.getAttributes().require(State.class);
    String path = helmPathUtils.buildAssetPath(state);
    AssetKind assetKind = context.getAttributes().require(AssetKind.class);
    context.getRepository().facet(HelmHostedFacet.class).upload(path, context.getRequest().getPayload(), assetKind);
    return ok();
  };

  public final Handler push = context -> {
    HelmHostedFacet hostedFacet = context.getRepository().facet(HelmHostedFacet.class);
    StorageFacet storageFacet = context.getRepository().facet(StorageFacet.class);

    PartPayload payload = getPartPayload(context);
    String fileName = payload.getName() != null ? payload.getName() : StringUtils.EMPTY;
    AssetKind assetKind = AssetKind.getAssetKindByFileName(fileName);
    try (TempBlob tempBlob = storageFacet.createTempBlob(payload, HASH_ALGORITHMS)) {
      String path = hostedFacet.getPath(tempBlob, assetKind);
      hostedFacet.upload(path, tempBlob, payload, assetKind);
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
    String path = helmPathUtils.buildAssetPath(state);

    boolean deleted = context.getRepository().facet(HelmHostedFacet.class).delete(path);

    return (deleted) ? ok() : notFound();
  };
}
