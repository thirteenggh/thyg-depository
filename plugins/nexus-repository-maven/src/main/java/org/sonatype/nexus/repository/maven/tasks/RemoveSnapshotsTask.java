package org.sonatype.nexus.repository.maven.tasks;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.maven.MavenFacet;
import org.sonatype.nexus.repository.maven.RemoveSnapshotsFacet;
import org.sonatype.nexus.repository.maven.VersionPolicy;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.scheduling.TaskConfiguration;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.maven.tasks.RemoveSnapshotsTaskDescriptor.GRACE_PERIOD;
import static org.sonatype.nexus.repository.maven.tasks.RemoveSnapshotsTaskDescriptor.MINIMUM_SNAPSHOT_RETAINED_COUNT;
import static org.sonatype.nexus.repository.maven.tasks.RemoveSnapshotsTaskDescriptor.REMOVE_IF_RELEASED;
import static org.sonatype.nexus.repository.maven.tasks.RemoveSnapshotsTaskDescriptor.SNAPSHOT_RETENTION_DAYS;

/**
 * Task to remove snapshots from a Maven repository.
 *
 * @since 3.0
 */
@Named
public class RemoveSnapshotsTask
    extends RepositoryTaskSupport
{
  private final Format maven2Format;

  @Inject
  public RemoveSnapshotsTask(@Named(Maven2Format.NAME) final Format maven2Format)
  {
    this.maven2Format = checkNotNull(maven2Format);
  }

  @Override
  protected void execute(final Repository repository) {
    if (hasBeenProcessed(repository)) {
      log.debug("Skipping repository '{}'; it has already been processed", repository.getName());
      return;
    }

    if (isGroupRepository(repository)) {
      markProcessed(repository); // nothing to do for the group itself

      repository.facet(GroupFacet.class).members().stream().filter(this::appliesTo).forEach(this::execute);
    }
    else {
      log.info("Executing removal of snapshots on repository '{}'", repository.getName());

      TaskConfiguration config = getConfiguration();
      RemoveSnapshotsConfig removeSnapshotsConfig = new RemoveSnapshotsConfig(
          config.getInteger(MINIMUM_SNAPSHOT_RETAINED_COUNT, 1),
          config.getInteger(SNAPSHOT_RETENTION_DAYS, 30),
          config.getBoolean(REMOVE_IF_RELEASED, false),
          config.getInteger(GRACE_PERIOD, -1));
      repository.facet(RemoveSnapshotsFacet.class).removeSnapshots(removeSnapshotsConfig);
      markProcessed(repository);
    }
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return maven2Format.equals(repository.getFormat())
        && repository.optionalFacet(RemoveSnapshotsFacet.class).isPresent()
        && repository.facet(MavenFacet.class).getVersionPolicy() != VersionPolicy.RELEASE;
  }

  @Override
  public String getMessage() {
    return "Remove Maven snapshots from " + getRepositoryField();
  }
}
