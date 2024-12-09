package org.sonatype.nexus.repository.upload;

import java.util.Optional;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.view.Content;

/**
 * Process uploaded content
 *
 * @since 3.29
 */
public interface UploadProcessor
{
  /**
   * Extract {@link EntityId} of entity in the content
   *
   * @param content uploaded content
   * @return {@link EntityId} of entity from content
   */
  Optional<EntityId> extractId(final Content content);
}
