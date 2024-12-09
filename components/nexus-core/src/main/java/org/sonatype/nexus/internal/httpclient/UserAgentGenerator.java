package org.sonatype.nexus.internal.httpclient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationVersion;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Generates the {@code User-Agent} header value.
 *
 * @since 3.0
 */
@Named
@Singleton
public class UserAgentGenerator
  extends ComponentSupport
{
  private final ApplicationVersion applicationVersion;

  private String value;

  private String edition;

  @Inject
  public UserAgentGenerator(final ApplicationVersion applicationVersion) {
    this.applicationVersion = checkNotNull(applicationVersion);
  }

  public String generate() {
    // Cache platform details or re-cache if the edition has changed
    if (value == null || !applicationVersion.getEdition().equals(edition)) {
      // track edition for cache invalidation
      edition = applicationVersion.getEdition();

      value = String.format("Nexus/%s (%s; %s; %s; %s; %s)",
          applicationVersion.getVersion(),
          edition,
          System.getProperty("os.name"),
          System.getProperty("os.version"),
          System.getProperty("os.arch"),
          System.getProperty("java.version"));
    }

    return value;
  }
}
