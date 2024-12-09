package org.sonatype.nexus.datastore;

import org.sonatype.nexus.datastore.api.DataStoreConfiguration;

/**
 * Restores DataStores from backup.
 *
 * @since 3.21
 */
public interface DataStoreRestorer
{
  boolean maybeRestore(DataStoreConfiguration dataStoreConfiguration);
}
