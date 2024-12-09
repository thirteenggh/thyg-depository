package org.sonatype.nexus.internal.status;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.system.FileDescriptorService;

import static java.lang.String.format;

/**
 * Health check that indicates if the file descriptor limit is below the recommended threshold
 *
 * @since 3.16
 */
@Named("文件描述符")
@Singleton
public class FileDescriptorHealthCheck
    extends HealthCheckComponentSupport
{
  private FileDescriptorService fileDescriptorService;

  @Inject
  public FileDescriptorHealthCheck(final FileDescriptorService fileDescriptorService) {
    this.fileDescriptorService = fileDescriptorService;
  }

  @Override
  protected Result check() {
    return fileDescriptorService.isFileDescriptorLimitOk() ? Result.healthy() : Result.unhealthy(reason());
  }

  private String reason() {
    return format("推荐文件描述符限制为%d，但当前为%d",
        fileDescriptorService.getFileDescriptorRecommended(), fileDescriptorService.getFileDescriptorCount());
  }

}
