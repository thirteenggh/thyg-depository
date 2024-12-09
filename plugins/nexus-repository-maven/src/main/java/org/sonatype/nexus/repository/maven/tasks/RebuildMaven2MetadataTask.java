package org.sonatype.nexus.repository.maven.tasks;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.RepositoryTaskSupport;
import org.sonatype.nexus.repository.Type;
import org.sonatype.nexus.repository.maven.MavenMetadataRebuildFacet;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.types.HostedType;
import org.sonatype.nexus.scheduling.Cancelable;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.maven.tasks.RebuildMaven2MetadataTaskDescriptor.ARTIFACTID_FIELD_ID;
import static org.sonatype.nexus.repository.maven.tasks.RebuildMaven2MetadataTaskDescriptor.BASEVERSION_FIELD_ID;
import static org.sonatype.nexus.repository.maven.tasks.RebuildMaven2MetadataTaskDescriptor.GROUPID_FIELD_ID;
import static org.sonatype.nexus.repository.maven.tasks.RebuildMaven2MetadataTaskDescriptor.REBUILD_CHECKSUMS;

/**
 * Maven 2 metadata rebuild task.
 *
 * @since 3.0
 */
@Named
public class RebuildMaven2MetadataTask
    extends RepositoryTaskSupport
    implements Cancelable
{

  private final Type hostedType;

  private final Format maven2Format;

  @Inject
  public RebuildMaven2MetadataTask(@Named(HostedType.NAME) final Type hostedType,
                                   @Named(Maven2Format.NAME) final Format maven2Format)
  {
    this.hostedType = checkNotNull(hostedType);
    this.maven2Format = checkNotNull(maven2Format);
  }


  @Override
  protected void execute(final Repository repository) {
    MavenMetadataRebuildFacet mavenHostedFacet = repository.facet(MavenMetadataRebuildFacet.class);
    mavenHostedFacet.rebuildMetadata(
        getConfiguration().getString(GROUPID_FIELD_ID),
        getConfiguration().getString(ARTIFACTID_FIELD_ID),
        getConfiguration().getString(BASEVERSION_FIELD_ID),
        getConfiguration().getBoolean(REBUILD_CHECKSUMS, false),
        false
    );
  }

  @Override
  protected boolean appliesTo(final Repository repository) {
    return maven2Format.equals(repository.getFormat()) && hostedType.equals(repository.getType());
  }

  @Override
  public String getMessage() {
    return "Rebuilding Maven Metadata of " + getRepositoryField();
  }

}
