package org.sonatype.nexus.internal.security.apikey;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link PurgeApiKeysTask}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class PurgeApiKeysTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "security.purge-api-keys";

  public PurgeApiKeysTaskDescriptor() {
    super(TYPE_ID,
        PurgeApiKeysTask.class,
        "Admin - Delete orphaned API keys",
        VISIBLE,
        EXPOSED
    );
  }
}
