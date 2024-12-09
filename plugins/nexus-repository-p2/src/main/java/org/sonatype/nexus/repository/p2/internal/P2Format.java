package org.sonatype.nexus.repository.p2.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * P2 repository format.
 */
@Named(P2Format.NAME)
@Singleton
public class P2Format
    extends Format
{
  public static final String NAME = "p2";

  public P2Format() {
    super(NAME);
  }
}
