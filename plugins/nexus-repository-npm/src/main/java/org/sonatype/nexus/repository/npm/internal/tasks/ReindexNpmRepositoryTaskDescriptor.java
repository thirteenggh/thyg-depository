package org.sonatype.nexus.repository.npm.internal.tasks;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.formfields.RepositoryCombobox;
import org.sonatype.nexus.repository.npm.internal.search.v1.NpmSearchFacet;
import org.sonatype.nexus.repository.npm.internal.tasks.orient.OrientReindexNpmRepositoryTask;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link OrientReindexNpmRepositoryTask}.
 *
 * @since 3.7
 */
@Named
@Singleton
public class ReindexNpmRepositoryTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "repository.npm.reindex";

  public static final String REPOSITORY_NAME_FIELD_ID = "repositoryName";

  @Inject
  public ReindexNpmRepositoryTaskDescriptor(final NodeAccess nodeAccess) {
    super(TYPE_ID,
        ReindexNpmRepositoryTask.class,
        "Repair - Reconcile npm /-/v1/search metadata",
        VISIBLE,
        EXPOSED,
        new RepositoryCombobox(
            REPOSITORY_NAME_FIELD_ID,
            "Repository",
            "Select the npm repository to reconcile",
            true
        ).includingAnyOfFacets(NpmSearchFacet.class).includeAnEntryForAllRepositories(),

        nodeAccess.isClustered() ? newMultinodeFormField().withInitialValue(true) : null
    );
  }
}
