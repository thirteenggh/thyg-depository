package org.sonatype.nexus.repository.p2.upgrade.tasks;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.repository.p2.upgrade.P2Upgrade_1_2;
import org.sonatype.nexus.scheduling.TaskScheduler;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.TASKS;
import static org.sonatype.nexus.repository.p2.upgrade.P2Upgrade_1_2.MARKER_FILE;
import static org.sonatype.nexus.repository.p2.upgrade.tasks.P2RewriteCompositeMetadataTaskDescriptor.TYPE_ID;

/**
 * Instantiates rewriting composite metadata sites to use site hashes based on the existence of a marker
 * file created by the upgrade {@link P2Upgrade_1_2}.
 *
 * @since 1.1
 */
@Named
@Singleton
@ManagedLifecycle(phase = TASKS)
public class P2RewriteCompositeMetadataService
    extends LifecycleSupport
{
  private final Path markerFile;

  private final TaskScheduler taskScheduler;

  @Inject
  public P2RewriteCompositeMetadataService(
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
