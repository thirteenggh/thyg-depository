package org.sonatype.nexus.internal.security.realm;

import org.sonatype.nexus.datastore.api.SingletonDataAccess;

/**
 * {@link RealmConfigurationData} access.
 *
 * @since 3.21
 */
public interface RealmConfigurationDAO
    extends SingletonDataAccess<RealmConfigurationData>
{
  // no additional behaviour
}
