package org.sonatype.nexus.repository.storage.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * @since 3.2.1
 */
@Named
@Singleton
public class StorageFacetCleanupTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "repository.storage-facet-cleanup";

  @Inject
  public StorageFacetCleanupTaskDescriptor() {
    super(TYPE_ID, StorageFacetCleanupTask.class, "Storage facet cleanup", NOT_VISIBLE, NOT_EXPOSED);
  }
}
