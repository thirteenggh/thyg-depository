package org.sonatype.nexus.repository.content.importtask;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;

/**
 * Provides some post processing capabilities for assets on the import task
 *
 * @since 3.29
 */
public interface ImportPostProcessor
{
  void attributePostProcessing(Repository repository, Asset asset);
}
