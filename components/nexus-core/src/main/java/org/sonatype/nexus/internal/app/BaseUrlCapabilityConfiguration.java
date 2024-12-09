package org.sonatype.nexus.internal.app;

import java.util.Map;

import org.sonatype.nexus.capability.CapabilityConfigurationSupport;
import org.sonatype.nexus.validation.constraint.UrlString;

import org.hibernate.validator.constraints.NotBlank;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link BaseUrlCapability} configuration.
 *
 * @since 3.0
 */
public class BaseUrlCapabilityConfiguration
    extends CapabilityConfigurationSupport
{
  public static final String URL = "url";

  @NotBlank
  @UrlString
  private String url;

  public BaseUrlCapabilityConfiguration(final Map<String,String> properties) {
    checkNotNull(properties);
    this.url = properties.get(URL);
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "url='" + url + '\'' +
        '}';
  }
}
