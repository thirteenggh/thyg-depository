package org.sonatype.nexus.repository.apt.internal.snapshot;

import java.util.List;

import org.sonatype.nexus.repository.apt.internal.debian.Release;

/**
 * @since 3.17
 */
public class AllSnapshotComponentSelector
    implements SnapshotComponentSelector
{

  @Override
  public List<String> getArchitectures(final Release release) {
    return release.getArchitectures();
  }

  @Override
  public List<String> getComponents(final Release release) {
    return release.getComponents();
  }

}
