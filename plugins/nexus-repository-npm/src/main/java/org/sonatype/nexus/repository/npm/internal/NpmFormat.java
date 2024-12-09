package org.sonatype.nexus.repository.npm.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * npm repository format.
 *
 * @since 3.0
 */
@Named(NpmFormat.NAME)
@Singleton
public class NpmFormat
    extends Format
{
  public static final String NAME = "npm";

  public NpmFormat() {
    super(NAME);
  }
}
