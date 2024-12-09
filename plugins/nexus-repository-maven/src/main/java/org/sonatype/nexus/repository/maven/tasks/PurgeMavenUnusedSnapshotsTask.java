package org.sonatype.nexus.repository.maven.tasks;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.maven.PurgeUnusedSnapshotsFacet;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.Cancelable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Task to purge unused snapshots of the given Maven repository.
 *
 * @since 3.0
 */
@Named
public class PurgeMavenUnusedSnapshotsTask
    extends RepositoryTaskSupport
    implements Cancelable
{
  public static final String LAST_USED_FIELD_ID = "lastUsed";

  private final Type groupType;

  private final Type hostedType;

  private final Format maven2Format;

  @Inject
  public PurgeMavenUnusedSnapshotsTask(@Named(GroupType.NAME) final Type groupType,
                                       @Named(HostedType.NAME) final Type hostedType,
                                       @Named(Maven2Format.NAME) final Format maven2Format)
  {
    this.groupType = checkNotNull(groupType);
    this.hostedType = checkNotNull(hostedType);
    this.maven2Format = checkNotNull(maven2Format);
  }

  @Override
  protected void execute(final Repository repository) {
    repository.facet(PurgeUnusedSnapshotsFacet.class)
        .purgeUnusedSnapshots(getConfiguration().getInteger(LAST_USED_FIELD_ID, -1));
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return maven2Format.equals(repository.getFormat())
        && (hostedType.equals(repository.getType()) || groupType.equals(repository.getType()));
  }

  @Override
  public String getMessage() {
    return "Purge unused Maven snapshot versions from " + getRepositoryField();
  }
}
