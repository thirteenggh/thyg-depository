package org.sonatype.nexus.security.authz;

import java.util.Date;

import org.sonatype.nexus.security.ClientInfo;

/**
 * Event fired in case of an authorization is tried against given resource.
 */
public class NexusAuthorizationEvent
{
  private final ClientInfo clientInfo;

  private final ResourceInfo resourceInfo;

  private final boolean successful;

  private final Date date;

  public NexusAuthorizationEvent(final ClientInfo info, final ResourceInfo resInfo, final boolean successful) {
    this.clientInfo = info;
    this.resourceInfo = resInfo;
    this.successful = successful;
    this.date = new Date();
  }

  public ClientInfo getClientInfo() {
    return clientInfo;
  }

  public ResourceInfo getResourceInfo() {
    return resourceInfo;
  }

  public boolean isSuccessful() {
    return successful;
  }

  public Date getEventDate() {
    return date;
  }
}
