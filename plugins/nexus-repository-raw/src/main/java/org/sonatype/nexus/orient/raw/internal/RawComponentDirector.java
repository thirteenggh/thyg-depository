package org.sonatype.nexus.orient.raw.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentDirector;

/**
 * @since 3.10
 */
@Named("raw")
@Singleton
public class RawComponentDirector
    implements ComponentDirector
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

