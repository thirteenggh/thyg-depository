package org.sonatype.nexus.internal.httpclient.orient;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.httpclient.config.AuthenticationConfiguration;
import org.sonatype.nexus.httpclient.config.ConnectionConfiguration;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.httpclient.config.ProxyConfiguration;
import org.sonatype.nexus.internal.httpclient.AuthenticationConfigurationDeserializer;

import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.RedirectStrategy;

/**
 * HTTP-client configuration.
 *
 * @since 3.0
 */
public class OrientHttpClientConfiguration
    extends AbstractEntity
    implements HttpClientConfiguration, Cloneable
{
  OrientHttpClientConfiguration() { }

  @Valid
  @Nullable
  private ConnectionConfiguration connection;

  @Valid
  @Nullable
  private ProxyConfiguration proxy;

  @Valid
  @Nullable
  private RedirectStrategy redirectStrategy;

  /**
   * @see AuthenticationConfigurationDeserializer
   */
  @Valid
  @Nullable
  private AuthenticationConfiguration authentication;

  @Valid
  @Nullable
  private AuthenticationStrategy authenticationStrategy;

  @Valid
  @Nullable
  private Boolean shouldNormalizeUri;

  @Valid
  @Nullable
  private Boolean disableContentCompression;

  @Nullable
  public ConnectionConfiguration getConnection() {
    return connection;
  }

  public void setConnection(@Nullable final ConnectionConfiguration connection) {
    this.connection = connection;
  }

  @Nullable
  public ProxyConfiguration getProxy() {
    return proxy;
  }

  public void setProxy(@Nullable final ProxyConfiguration proxy) {
    this.proxy = proxy;
  }

  @Nullable
  public AuthenticationConfiguration getAuthentication() {
    return authentication;
  }

  public void setAuthentication(@Nullable final AuthenticationConfiguration authentication) {
    this.authentication = authentication;
  }

  @Nullable
  @Override
  public AuthenticationStrategy getAuthenticationStrategy() {
    return authenticationStrategy;
  }

  @Override
  public void setAuthenticationStrategy(
      @Nullable final AuthenticationStrategy authenticationStrategy)
  {
    this.authenticationStrategy = authenticationStrategy;
  }

  @Nullable
  public RedirectStrategy getRedirectStrategy() {
    return redirectStrategy;
  }

  public void setRedirectStrategy(@Nullable final RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  @Override
  public Boolean getNormalizeUri() {
    return shouldNormalizeUri;
  }

  @Override
  public void setNormalizeUri(final Boolean normalizeUri) {
    this.shouldNormalizeUri = normalizeUri;
  }

  @Override
  public Boolean getDisableContentCompression() {
    return disableContentCompression;
  }

  @Override
  public void setDisableContentCompression(final Boolean disableContentCompression) {
    this.disableContentCompression = disableContentCompression;
  }

  public OrientHttpClientConfiguration copy() {
    try {
      OrientHttpClientConfiguration copy = (OrientHttpClientConfiguration) clone();
      if (connection != null) {
        copy.connection = connection.copy();
      }
      if (proxy != null) {
        copy.proxy = proxy.copy();
      }
      if (authentication != null) {
        copy.authentication = authentication.copy();
      }
      if (redirectStrategy != null) {
        // no real cloning/copying needed, as we are allowed to use a singleton instance
        copy.redirectStrategy = redirectStrategy;
      }
      copy.shouldNormalizeUri = shouldNormalizeUri;
      copy.disableContentCompression = disableContentCompression;
      return copy;
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "connection=" + connection +
        ", proxy=" + proxy +
        ", authentication=" + authentication +
        '}';
  }
}
