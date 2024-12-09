package org.sonatype.nexus.internal.wonderland

import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicLong

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.common.app.ApplicationDirectories
import org.sonatype.nexus.common.wonderland.AuthTicketService
import org.sonatype.nexus.common.wonderland.DownloadService

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull

/**
 * Default {@link DownloadService}.
 *
 * @since 2.8
 */
@Named("default")
@Singleton
class DownloadServiceImpl
    extends ComponentSupport
    implements DownloadService
{

  /**
   * Counter used to generate unique names.
   */
  private static final AtomicLong counter = new AtomicLong()

  /**
   * Directory where files to be downloaded are stored.
   */
  private final File downloadDir

  private final AuthTicketService authTickets

  @Inject
  DownloadServiceImpl(final ApplicationDirectories applicationDirectories,
                      final AuthTicketService authTicketService)
  {
    checkNotNull(applicationDirectories)
    this.authTickets = checkNotNull(authTicketService)

    // resolve where files to be downloaded will be stored
    downloadDir = applicationDirectories.getWorkDirectory('downloads')
    log.info 'Downloads directory: {}', downloadDir
  }

  @Override
  Download get(String fileName, String authTicket) {
    log.info 'Download: {}', fileName

    if (!authTickets.redeemTicket(authTicket)) {
      throw new IllegalArgumentException('Invalid authentication ticket')
    }

    def file = new File(downloadDir, fileName)

    ensureWithinDownloads(file)

    if (!file.exists() && file.isFile()) {
      log.warn 'File {} not found in download directory (or is not a file)', file
      return null
    }
    return new Download(file.length(), new FileInputStream(file))
  }

  @Override
  String move(File source, String name) {
    def target = new File(downloadDir, name)
    ensureWithinDownloads(target)
    Files.move(source.toPath(), target.toPath())
    log.debug 'Moved {} to {}', source, target
    return target.getAbsolutePath()
  }

  @Override
  String uniqueName(String prefix) {
    return prefix + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + "-" + counter.incrementAndGet()
  }

  /**
   *  Ensure we do not leak references outside of the downloads directory, only direct children can be served
   */
  private void ensureWithinDownloads(File file) {
    checkArgument(file.parentFile == downloadDir, "Reference outside of downloads dir: $file")
  }
}
