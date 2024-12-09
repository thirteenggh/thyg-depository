package org.sonatype.nexus.repository.httpclient;

/**
 * Listener interface for changes to a repositories connection status.
 *
 * @since 3.3
 */
public interface RemoteConnectionStatusObserver
{
  void onStatusChanged(RemoteConnectionStatus oldStatus, RemoteConnectionStatus newStatus);
}
