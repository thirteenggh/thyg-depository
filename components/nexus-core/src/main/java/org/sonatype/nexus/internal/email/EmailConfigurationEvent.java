package org.sonatype.nexus.internal.email;

import org.sonatype.nexus.email.EmailConfiguration;

/**
 * Event for {@link EmailConfiguration} entity.
 *
 * @since 3.2
 */
public interface EmailConfigurationEvent
{
  boolean isLocal();

  String getRemoteNodeId();

  EmailConfiguration getEmailConfiguration();
}
