package org.sonatype.nexus.content.example.internal.recipe;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Format;

/**
 * Example repository format.
 *
 * @since 3.24
 */
@Named(ExampleFormat.NAME)
@Singleton
public class ExampleFormat
    extends Format
{
  public static final String NAME = "example";

  public ExampleFormat() {
    super(NAME);
  }
}
