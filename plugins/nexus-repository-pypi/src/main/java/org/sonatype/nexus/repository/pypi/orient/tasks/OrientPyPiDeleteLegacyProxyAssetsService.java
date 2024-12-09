package org.sonatype.nexus.repository.pypi.orient.tasks;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.repository.pypi.upgrade.PyPiUpgrade_1_2;
import org.sonatype.nexus.scheduling.TaskScheduler;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;
import static org.sonatype.nexus.repository.pypi.orient.tasks.OrientPyPiDeleteLegacyProxyAssetsTaskDescriptor.TYPE_ID;
import static org.sonatype.nexus.repository.pypi.upgrade.PyPiUpgrade_1_2.MARKER_FILE;

/**
 * Instantiates the legacy pypi proxy asset deletion task based on the existence of a marker
 * file created by the upgrade {@link PyPiUpgrade_1_2}.
 *
 * @since 3.22
 */
@Named
@Singleton
@ManagedLifecycle(phase = TASKS)
public class OrientPyPiDeleteLegacyProxyAssetsService
    extends LifecycleSupport
{
  private final Path markerFile;

  private final TaskScheduler taskScheduler;

  @Inject
  public OrientPyPiDeleteLegacyProxyAssetsService(
      final ApplicationDirectories directories,
      final TaskScheduler taskScheduler)
  {
    markerFile = new File(directories.getWorkDirectory("db"), MARKER_FILE).toPath();
    this.taskScheduler = checkNotNull(taskScheduler);
  }

  @Override
  protected void doStart() throws Exception {
    if (Files.exists(markerFile)) {
      log.info("Scheduling task: {}", TYPE_ID);
      taskScheduler.submit(taskScheduler.createTaskConfigurationInstance(TYPE_ID));
    }
  }
}
