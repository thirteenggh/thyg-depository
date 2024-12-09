package org.sonatype.nexus.repository.browse.node;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.formfields.ItemselectFormField;
import org.sonatype.nexus.repository.types.GroupType;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link RebuildBrowseNodesTask}.
 *
 * @since 3.6
 */
@Named
@Singleton
public class RebuildBrowseNodesTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "create.browse.nodes";

  public static final String REPOSITORY_NAME_FIELD_ID = "repositoryName";

  public static final String TASK_NAME = "Repair - Rebuild repository browse";

  @Inject
  public RebuildBrowseNodesTaskDescriptor(final NodeAccess nodeAccess, final GroupType groupType) {
    super(TYPE_ID, RebuildBrowseNodesTask.class, TASK_NAME, VISIBLE, EXPOSED,
        new ItemselectFormField(REPOSITORY_NAME_FIELD_ID, "Repository", "Select the repository(ies) to rebuild browse tree",
            true).withStoreApi("coreui_Repository.readReferencesAddingEntryForAll")
            .withButtons("up", "add", "remove", "down").withFromTitle("Available").withToTitle("Selected")
            .withStoreFilter("type", "!" + groupType.getValue()).withValueAsString(true),
        nodeAccess.isClustered() ? newLimitNodeFormField() : null);
  }
}
