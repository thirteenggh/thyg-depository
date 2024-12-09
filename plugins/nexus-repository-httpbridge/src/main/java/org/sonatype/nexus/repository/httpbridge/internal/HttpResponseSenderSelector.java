package org.sonatype.nexus.repository.httpbridge.internal;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.httpbridge.HttpResponseSender;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Response sender selector.
 *
 * @since 3.0
 */
@Singleton
@Named
class HttpResponseSenderSelector
    extends ComponentSupport
{
  private final Map<String, HttpResponseSender> responseSenders;

  private final DefaultHttpResponseSender defaultHttpResponseSender;

  @Inject
  public HttpResponseSenderSelector(final Map<String, HttpResponseSender> responseSenders,
                                    final DefaultHttpResponseSender defaultHttpResponseSender)
  {
    this.responseSenders = checkNotNull(responseSenders);
    this.defaultHttpResponseSender = checkNotNull(defaultHttpResponseSender);
  }

  /**
   * Returns the default sender.
   */
  @Nonnull
  public HttpResponseSender defaultSender() {
    return defaultHttpResponseSender;
  }

  /**
   * Find sender for repository format.
   *
   * If no format-specific sender is configured, the default is used.
   */
  @Nonnull
  public HttpResponseSender sender(final Repository repository) {
    String format = repository.getFormat().getValue();
    log.debug("Looking for HTTP response sender: {}", format);
    HttpResponseSender sender = responseSenders.get(format);
    if (sender == null) {
      return defaultHttpResponseSender;
    }
    return sender;
  }
}
