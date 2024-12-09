package com.sonatype.nexus.edition.oss;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationVersion;
import org.sonatype.nexus.common.app.ApplicationVersionSupport;

/**
 * OSS {@link ApplicationVersion}.
 *
 * @since 3.0
 */
@Named("OSS")
@Singleton
public class ApplicationVersionImpl
    extends ApplicationVersionSupport
{
  @Override
  public String getEdition() {
    return "OSS";
  }
}
