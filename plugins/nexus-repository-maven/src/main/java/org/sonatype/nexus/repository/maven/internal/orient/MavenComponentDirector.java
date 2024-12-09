package org.sonatype.nexus.repository.maven.internal.orient;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.MavenHostedFacet;
import org.sonatype.nexus.repository.maven.VersionPolicy;
import org.sonatype.nexus.repository.storage.Component;
import org.sonatype.nexus.repository.storage.ComponentDirector;

import static org.sonatype.nexus.repository.maven.VersionPolicy.MIXED;
import static org.sonatype.nexus.repository.maven.VersionPolicy.RELEASE;
import static org.sonatype.nexus.repository.maven.VersionPolicy.SNAPSHOT;
import static org.sonatype.nexus.repository.maven.internal.orient.MavenFacetUtils.isSnapshot;

/**
 * @since 3.10
 */
@Named("maven2")
@Singleton
public class MavenComponentDirector
    implements ComponentDirector
{
  @Override
  public boolean allowMoveTo(final Repository destination) {
    return true;
  }

  @Override
  public boolean allowMoveTo(final Component component, final Repository destination) {
    VersionPolicy versionPolicy = destination.facet(MavenFacet.class).getVersionPolicy();
    if (MIXED.equals(versionPolicy)) {
      return true;
    }
    if (isSnapshot(component)) {
      return SNAPSHOT.equals(versionPolicy);
    }
    else {
      return RELEASE.equals(versionPolicy);
    }
  }

  @Override
  public boolean allowMoveFrom(final Repository source) {
    return true;
  }

  @Override
  public void afterMove(final List<Map<String, String>> components, final Repository destination) {
    destination.optionalFacet(MavenHostedFacet.class).ifPresent(f ->
        components.stream()
            .map(component -> component.get("group"))
            .distinct()
            .forEach(group -> f.rebuildMetadata(group, null, null, false))
    );
  }
}
