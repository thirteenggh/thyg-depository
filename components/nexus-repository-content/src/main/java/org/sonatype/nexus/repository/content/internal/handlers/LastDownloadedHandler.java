package org.sonatype.nexus.repository.content.internal.handlers;

import java.time.OffsetDateTime;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.time.UTC;
import org.sonatype.nexus.repository.capability.GlobalRepositorySettings;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.Status;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.http.HttpMethods.GET;
import static org.sonatype.nexus.repository.http.HttpMethods.HEAD;

/**
 * Updates the asset last downloaded time.
 *
 * @since 3.24
 */
@Named
@Singleton
public class LastDownloadedHandler
    extends ComponentSupport
    implements org.sonatype.nexus.repository.view.handlers.LastDownloadedHandler
{
  private final GlobalRepositorySettings globalSettings;

  @Inject
  public LastDownloadedHandler(final GlobalRepositorySettings globalSettings) {
    this.globalSettings = checkNotNull(globalSettings);
  }

  @Override
  public Response handle(final Context context) throws Exception {
    Response response = context.proceed();

    try {
      if (isSuccessfulRequestWithContent(context, response)) {
        Content content = (Content) response.getPayload();
        maybeUpdateLastDownloaded(content.getAttributes());
      }
    }
    catch (Exception e) {
      log.error("Failed to update last downloaded time for request {}", context.getRequest().getPath(), e);
    }

    return response;
  }

  protected void maybeUpdateLastDownloaded(final AttributesMap attributes) {
    maybeUpdateLastDownloaded(attributes.get(Asset.class));
  }

  protected void maybeUpdateLastDownloaded(@Nullable final Asset asset) {
    if (asset != null && !isNextUpdateInFuture(asset.lastDownloaded())) {
      if (asset instanceof FluentAsset) {
        ((FluentAsset) asset).markAsDownloaded();
      }
      else {
        log.debug("Cannot mark read-only asset {} as downloaded", asset.path());
      }
    }
  }

  private boolean isNextUpdateInFuture(Optional<OffsetDateTime> lastTime) {
    return lastTime.isPresent() && lastTime.get().plus(globalSettings.getLastDownloadedInterval()).isAfter(UTC.now());
  }

  private boolean isSuccessfulRequestWithContent(final Context context, final Response response) {
    return isGetOrHeadRequest(context)
        && isSuccessfulOrNotModified(response.getStatus())
        && response.getPayload() instanceof Content;
  }

  private boolean isGetOrHeadRequest(final Context context) {
    String action = context.getRequest().getAction();
    return GET.equals(action) || HEAD.equals(action);
  }

  private boolean isSuccessfulOrNotModified(final Status status) {
    return status.isSuccessful() || status.getCode() == 304;
  }
}
