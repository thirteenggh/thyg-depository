package org.sonatype.nexus.repository.cocoapods.tasks;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.repository.cocoapods.upgrade.CocoapodsUpgrade_1_1;
import org.sonatype.nexus.scheduling.TaskScheduler;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;
import static org.sonatype.nexus.repository.cocoapods.tasks.CocoapodsStoreRemoteUrlInAttributesTaskDescriptor.TYPE_ID;
import static org.sonatype.nexus.repository.cocoapods.upgrade.CocoapodsUpgrade_1_1.MARKER_FILE;

/**
 * Instantiates the change download URL storage method task based on the existence of a marker
 * file created by the upgrade {@link CocoapodsUpgrade_1_1}.
 *
 * @since 3.27
 */
@Named
@Singleton
@ManagedLifecycle(phase = TASKS)
public class CocoapodsStoreRemoteUrlInAttributesService
    extends LifecycleSupport
{
  private final Path markerFile;

  private final TaskScheduler taskScheduler;

  @Inject
  public CocoapodsStoreRemoteUrlInAttributesService(
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

