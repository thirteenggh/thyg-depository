package org.sonatype.nexus.repository.maven.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * Maven 2 format.
 *
 * @since 3.0
 */
@Named(Maven2Format.NAME)
@Singleton
public class Maven2Format
    extends Format
{
  public static final String NAME = "maven2";

  public Maven2Format() {
    super(NAME);
  }
}
