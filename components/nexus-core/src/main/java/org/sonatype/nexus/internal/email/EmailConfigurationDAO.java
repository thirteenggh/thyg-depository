package org.sonatype.nexus.internal.email;

import org.sonatype.nexus.datastore.api.SingletonDataAccess;

/**
 * {@link EmailConfigurationData} access.
 *
 * @since 3.21
 */
public interface EmailConfigurationDAO
    extends SingletonDataAccess<EmailConfigurationData>
{
  // no additional behaviour...
}
