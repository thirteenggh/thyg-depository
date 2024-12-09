package org.sonatype.nexus.repository.maven.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link PublishMavenIndexTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class PublishMavenIndexTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "repository.maven.publish-dotindex";

  public static final String REPOSITORY_NAME_FIELD_ID = "repositoryName";

  public PublishMavenIndexTaskDescriptor() {
    super(TYPE_ID,
        PublishMavenIndexTask.class,
        "Maven - Publish Maven Indexer files",
        VISIBLE,
        EXPOSED,
        new RepositoryCombobox(
            REPOSITORY_NAME_FIELD_ID,
            "Repository",
            "Select the Maven repository to publish indexer files for",
            true
        ).includingAnyOfFormats(Maven2Format.NAME).includeAnEntryForAllRepositories()
    );
  }
}
