package org.sonatype.nexus.repository.httpclient;

/**
 * Connection status of a remote repository
 *
 * @since 3.3
 */
public enum RemoteConnectionStatusType
{
  UNINITIALISED("未初始化"),
  READY("连接就绪"),
  AVAILABLE("远程可用"),
  BLOCKED("远程被手动阻止"),
  AUTO_BLOCKED_UNAVAILABLE("远程被自动阻止且不可用"),
  UNAVAILABLE("远程不可用"),
  OFFLINE("存储库离线"),;

  private final String description;

  RemoteConnectionStatusType(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
