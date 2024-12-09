package org.sonatype.nexus.repository.r.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * R repository format.
 *
 * @since 3.28
 */
@Named(RFormat.NAME)
@Singleton
public class RFormat
    extends Format
{
  public static final String NAME = "r";

  public RFormat() {
    super(NAME);
  }
}
