package org.sonatype.nexus.repository.maven.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link UnpublishMavenIndexTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class UnpublishMavenIndexTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "repository.maven.unpublish-dotindex";

  public static final String REPOSITORY_NAME_FIELD_ID = "repositoryName";

  public UnpublishMavenIndexTaskDescriptor() {
    super(TYPE_ID,
        UnpublishMavenIndexTask.class,
        "Maven - Unpublish Maven Indexer files",
        VISIBLE,
        EXPOSED,
        new RepositoryCombobox(
            REPOSITORY_NAME_FIELD_ID,
            "Repository",
            "Select the Maven repository to remove indexes for",
            true
        ).includingAnyOfFormats(Maven2Format.NAME).includeAnEntryForAllRepositories()
    );
  }
}
