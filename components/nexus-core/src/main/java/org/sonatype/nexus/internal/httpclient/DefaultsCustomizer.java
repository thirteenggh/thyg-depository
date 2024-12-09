package org.sonatype.nexus.internal.httpclient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ByteSize;
import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.goodies.common.Time;
import org.sonatype.nexus.httpclient.HttpClientPlan;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Applies defaults to {@link HttpClientPlan}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class DefaultsCustomizer
  extends ComponentSupport
  implements HttpClientPlan.Customizer
{
  private final UserAgentGenerator userAgentGenerator;

  private final Time requestTimeout;

  private final Time connectionRequestTimeout;

  private final Time keepAliveDuration;

  private final ByteSize bufferSize;

  @Inject
  public DefaultsCustomizer(
      final UserAgentGenerator userAgentGenerator,
      @Named("${nexus.httpclient.requestTimeout:-20s}") final Time requestTimeout,
      @Named("${nexus.httpclient.connectionRequestTimeout:-30s}") final Time connectionRequestTimeout,
      @Named("${nexus.httpclient.keepAliveDuration:-30s}") final Time keepAliveDuration,
      @Named("${nexus.httpclient.bufferSize:-8k}") final ByteSize bufferSize)
  {
    this.userAgentGenerator = checkNotNull(userAgentGenerator);

    this.requestTimeout = checkNotNull(requestTimeout);
    log.debug("Request timeout: {}", requestTimeout);

    this.connectionRequestTimeout = checkNotNull(connectionRequestTimeout);
    log.debug("Connection request timeout: {}", connectionRequestTimeout);

    this.keepAliveDuration = checkNotNull(keepAliveDuration);
    log.debug("Keep-alive duration: {}", keepAliveDuration);

    this.bufferSize = checkNotNull(bufferSize);
    log.debug("Buffer-size: {}", bufferSize);
  }

  @Override
  public void customize(final HttpClientPlan plan) {
    checkNotNull(plan);

    plan.setUserAgentBase(userAgentGenerator.generate());

    plan.getClient().setKeepAliveStrategy(new NexusConnectionKeepAliveStrategy(keepAliveDuration.toMillis()));
    plan.getClient().setRetryHandler(new StandardHttpRequestRetryHandler(2, false));

    plan.getConnection().setBufferSize(bufferSize.toBytesI());

    plan.getRequest().setConnectionRequestTimeout(connectionRequestTimeout.toMillisI());
    plan.getRequest().setCookieSpec(CookieSpecs.IGNORE_COOKIES);
    plan.getRequest().setExpectContinueEnabled(false);

    int requestTimeoutMillis = requestTimeout.toMillisI();
    plan.getSocket().setSoTimeout(requestTimeoutMillis);
    plan.getRequest().setConnectTimeout(requestTimeoutMillis);
    plan.getRequest().setSocketTimeout(requestTimeoutMillis);
  }
}
