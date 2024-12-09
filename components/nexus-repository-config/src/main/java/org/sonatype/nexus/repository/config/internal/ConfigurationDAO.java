package org.sonatype.nexus.repository.config.internal;

import org.sonatype.nexus.datastore.api.IterableDataAccess;

/**
 * {@link ConfigurationData} access.
 *
 * @since 3.21
 */
public interface ConfigurationDAO
    extends IterableDataAccess.WithName<ConfigurationData>
{
  // no additional behaviour
}
