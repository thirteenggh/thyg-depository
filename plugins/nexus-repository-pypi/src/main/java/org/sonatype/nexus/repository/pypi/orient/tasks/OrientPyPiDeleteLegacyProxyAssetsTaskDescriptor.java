package org.sonatype.nexus.repository.pypi.orient.tasks;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link OrientPyPiDeleteLegacyProxyAssetsTask}.
 *
 * @since 3.22
 */
@Named
@Singleton
public class OrientPyPiDeleteLegacyProxyAssetsTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TASK_NAME = "PyPi - Delete legacy proxy assets";

  public static final String TYPE_ID = "repository.pypi.delete-legacy-proxy-assets";

  public OrientPyPiDeleteLegacyProxyAssetsTaskDescriptor() {
    super(TYPE_ID, OrientPyPiDeleteLegacyProxyAssetsTask.class, TASK_NAME, VISIBLE, EXPOSED);
  }
}
