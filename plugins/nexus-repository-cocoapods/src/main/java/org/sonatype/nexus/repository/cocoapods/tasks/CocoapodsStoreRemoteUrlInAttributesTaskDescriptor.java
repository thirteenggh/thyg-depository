package org.sonatype.nexus.repository.cocoapods.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link CocoapodsStoreRemoteUrlInAttributesTask}.
 *
 * @since 3.27
 */
@Named
@Singleton
public class CocoapodsStoreRemoteUrlInAttributesTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TASK_NAME = "Cocoapods Proxy - Store remote Url in Attributes";

  public static final String TYPE_ID = "repository.cocoapods.store-remote-url-in-attributes";

  public CocoapodsStoreRemoteUrlInAttributesTaskDescriptor() {
    super(TYPE_ID, CocoapodsStoreRemoteUrlInAttributesTask.class, TASK_NAME, VISIBLE, EXPOSED);
  }
}
