package org.sonatype.nexus.repository.apt.internal.snapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.sonatype.nexus.repository.apt.internal.debian.ControlFile;
import org.sonatype.nexus.repository.apt.internal.debian.Release;

/**
 * @since 3.17
 */
public class FilteredSnapshotComponentSelector
    implements SnapshotComponentSelector
{

  private final ControlFile settings;

  public FilteredSnapshotComponentSelector(final ControlFile settings) {
    this.settings = settings;
  }

  @Override
  public List<String> getArchitectures(final Release release) {
    Optional<Set<String>> settingsArchitectures = settings.getField("Architectures")
        .map(s -> s.listValue())
        .map(l -> new HashSet<>(l));
    if (settingsArchitectures.isPresent()) {
      Set<String> releaseArchitectures = new HashSet<>(release.getArchitectures());
      releaseArchitectures.retainAll(settingsArchitectures.get());
      return new ArrayList<>(releaseArchitectures);
    }
    else {
      return release.getArchitectures();
    }
  }

  @Override
  public List<String> getComponents(final Release release) {
    Optional<Set<String>> settingsComponents = settings.getField("Components").map(s -> s.listValue())
        .map(l -> new HashSet<>(l));
    if (settingsComponents.isPresent()) {
      Set<String> releaseComponents = new HashSet<>(release.getComponents());
      releaseComponents.retainAll(settingsComponents.get());
      return new ArrayList<>(releaseComponents);
    }
    else {
      return release.getComponents();
    }
  }

}
