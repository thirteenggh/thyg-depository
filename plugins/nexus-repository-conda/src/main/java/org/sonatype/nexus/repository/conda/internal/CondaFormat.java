package org.sonatype.nexus.repository.conda.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * Conda repository format.
 *
 * @since 3.19
 */
@Named(CondaFormat.NAME)
@Singleton
public class CondaFormat
    extends Format
{
  public static final String NAME = "conda";

  CondaFormat() {
    super(NAME);
  }
}
