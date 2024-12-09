package org.sonatype.nexus.repository.apt.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * @since 3.17
 */
@Named(AptFormat.NAME)
@Singleton
public class AptFormat
    extends Format
{
  public static final String NAME = "apt";

  public AptFormat() {
    super(NAME);
  }
}
