package org.sonatype.nexus.coreui.internal.log;

import org.sonatype.nexus.common.log.LoggerLevel;

/**
 * @since 3.24
 */
public class UpdateLoggingConfigurationRequest
{
  private LoggerLevel level;

  public LoggerLevel getLevel() {
    return level;
  }

  public void setLevel(final LoggerLevel level) {
    this.level = level;
  }
}
