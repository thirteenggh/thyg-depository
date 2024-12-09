package org.sonatype.nexus.common.app;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.common.annotations.VisibleForTesting;

/**
 * Support for {@link ApplicationVersion} implementations.
 *
 * @since 3.0
 */
public abstract class ApplicationVersionSupport
  extends ComponentSupport
  implements ApplicationVersion
{
  public static final String UNKNOWN = "UNKNOWN";

  @VisibleForTesting
  static final String VERSION = "version";

  @VisibleForTesting
  static final String BUILD_REVISION = "build.revision";

  @VisibleForTesting
  static final String BUILD_TIMESTAMP = "build.timestamp";

  @VisibleForTesting
  static final String NEXUS2_VERSION = "nexus2.version";

  /**
   * Resource name of properties-file which contains version information.
   */
  private static final String RESOURCE_NAME = "version.properties";

  private Properties properties;

  /**
   * Load or return cached properties.
   */
  @VisibleForTesting
  Properties getProperties() {
    if (properties != null) {
      return properties;
    }

    Properties props = new Properties();
    URL url = ApplicationVersionSupport.class.getResource(RESOURCE_NAME);
    if (url != null) {
      log.debug("Loading properties from: {}", url);

      try (InputStream input = url.openStream()) {
        props.load(input);
        log.trace("Loaded properties: {}", props);
      }
      catch (Exception e) {
        log.error("Failed to load properties from: {}", url, e);
      }
    }
    else {
      log.error("Missing required resource: {}", RESOURCE_NAME);
    }

    properties = props;
    return properties;
  }

  private String property(final String key) {
    return getProperties().getProperty(key, UNKNOWN);
  }

  @Override
  public String getVersion() {
    return property(VERSION);
  }

  @Override
  public String getBrandedEditionAndVersion() {
    String edition = getEdition();
    String version = getVersion();
    return ("OSS".equals(edition) ? edition + " " : "") + version;
  }

  @Override
  public String getBuildRevision() {
    return property(BUILD_REVISION);
  }

  @Override
  public String getBuildTimestamp() {
    return property(BUILD_TIMESTAMP);
  }

  @Override
  public String getNexus2CompatibleVersion() {
    return property(NEXUS2_VERSION);
  }
}
