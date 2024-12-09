package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * PyPI repository format.
 *
 * @since 3.1
 */
@Named(PyPiFormat.NAME)
@Singleton
public class PyPiFormat
    extends Format
{
  public static final String NAME = "pypi";

  public PyPiFormat() {
    super(NAME);
  }
}
