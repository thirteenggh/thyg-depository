package org.sonatype.nexus.repository.golang;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * Go repository format.
 *
 * @since 3.17
 */
@Named(GolangFormat.NAME)
@Singleton
public class GolangFormat
    extends Format
{
  public static final String NAME = "go";

  public GolangFormat() {
    super(NAME);
  }
}
