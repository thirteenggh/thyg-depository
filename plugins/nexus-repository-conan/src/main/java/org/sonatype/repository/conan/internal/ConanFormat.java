package org.sonatype.repository.conan.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * Conan repository format.
 *
 * @since 3.28
 */
@Named(ConanFormat.NAME)
@Singleton
public class ConanFormat
    extends Format
{
  public static final String NAME = "conan";

  public ConanFormat() {
    super(NAME);
  }
}
