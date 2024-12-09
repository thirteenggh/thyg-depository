package org.sonatype.nexus.repository.importtask;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.StorageTx;

/**
 * Provides some post processing capabilities for assets on the import task
 * @since 3.28
 */
public interface ImportPostProcessor
{
  void attributePostProcessing(final Asset asset, final StorageTx tx, final Repository repository);
}
