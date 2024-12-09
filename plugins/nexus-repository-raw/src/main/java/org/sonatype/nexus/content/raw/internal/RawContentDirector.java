package org.sonatype.nexus.content.raw.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.director.ContentDirector;

/**
 * @since 3.27
 */
@Named("raw")
@Singleton
public class RawContentDirector
    implements ContentDirector
{
  @Override
  public boolean allowMoveTo(final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveTo(final Component component, final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveFrom(final Repository source) {
    return true;
  }
}
