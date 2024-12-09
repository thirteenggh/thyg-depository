package org.sonatype.nexus.internal.security.anonymous;

import org.sonatype.nexus.datastore.api.SingletonDataAccess;

/**
 * {@link AnonymousConfigurationData} access.
 *
 * @since 3.21
 */
public interface AnonymousConfigurationDAO
    extends SingletonDataAccess<AnonymousConfigurationData>
{
  // no additional behaviour
}
