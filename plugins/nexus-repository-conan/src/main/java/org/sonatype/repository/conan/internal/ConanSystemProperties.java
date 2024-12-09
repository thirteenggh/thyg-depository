package org.sonatype.repository.conan.internal;

/**
 * @since 3.28
 */
public class ConanSystemProperties
{
  private ConanSystemProperties() {
  }

  public static final String HOSTED_ENABLED_PROPERTY = "nexus.conan.hosted.enabled";

  public static final String PROXY_SEARCH_TIMEOUT = "nexus.conan.proxy.search.timeout";
}
