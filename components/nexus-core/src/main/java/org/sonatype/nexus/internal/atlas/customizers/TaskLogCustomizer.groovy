package org.sonatype.nexus.internal.atlas.customizers

import java.time.Instant
import java.time.ZonedDateTime

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.app.ApplicationDirectories
import org.sonatype.nexus.common.log.LogManager
import org.sonatype.nexus.logging.task.TaskLogHome
import org.sonatype.nexus.supportzip.FileContentSourceSupport
import org.sonatype.nexus.supportzip.SupportBundle
import org.sonatype.nexus.supportzip.SupportBundleCustomizer

import com.google.common.annotations.VisibleForTesting

import static com.google.common.base.Preconditions.checkNotNull
import static java.time.Instant.ofEpochMilli
import static org.apache.commons.io.FileUtils.iterateFiles
import static org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Priority.DEFAULT
import static org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type.TASKLOG

/**
 * Adds log files to support bundle.
 *
 * @since 3.5
 */
@Named
@Singleton
class TaskLogCustomizer
    extends ComponentSupport
    implements SupportBundleCustomizer
{
  private static final String[] extensions = ['log']

  private final LogManager logManager

  private final ApplicationDirectories applicationDirectories

  @Inject
  TaskLogCustomizer(final LogManager logManager,
                    final ApplicationDirectories applicationDirectories)
  {
    this.logManager = checkNotNull(logManager)
    this.applicationDirectories = checkNotNull(applicationDirectories)
  }

  @Override
  void customize(final SupportBundle supportBundle) {
    Instant cutoff = ZonedDateTime.now().minusHours(24).toInstant()
    String taskLogHome = getTaskLogHome()
    if (taskLogHome != null) {
      iterateFiles(new File(taskLogHome), extensions, false).each {
        if (ofEpochMilli(it.lastModified()).isAfter(cutoff)) {
          supportBundle << new FileContentSourceSupport(TASKLOG, "log/tasks/${it.name}", it, DEFAULT)
        }
        else {
          log.debug 'Skipping file [past 24 hours]: {}', it
        }
      }
    }
  }

  @VisibleForTesting
  String getTaskLogHome() {
    return TaskLogHome.getTaskLogHome()
  }
}
