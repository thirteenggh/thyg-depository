package org.sonatype.nexus.repository.p2.upgrade.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link P2RewriteCompositeMetadataTask}.
 *
 * @since 1.1
 */
@Named
@Singleton
public class P2RewriteCompositeMetadataTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TASK_NAME = "p2 - rewrite composite metdata";

  public static final String TYPE_ID = "repository.p2.rewrite-composite-metdata";

  public P2RewriteCompositeMetadataTaskDescriptor() {
    super(TYPE_ID, P2RewriteCompositeMetadataTask.class, TASK_NAME, VISIBLE, EXPOSED);
  }
}
