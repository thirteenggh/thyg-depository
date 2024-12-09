package org.sonatype.nexus.repository.rest;

import java.util.Collection;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.upload.ComponentUpload;


/**
 * Extension point interface used within {@link ComponentsResource#uploadComponent}
 *
 * @since 3.10
 */
public interface ComponentUploadExtension
{
  /**
   * Apply data in the {@link ComponentUpload} to the provided {@link EntityId} within
   * the provided {@link Repository}
   */
  void apply(Repository repository, ComponentUpload componentUpload, Collection<EntityId> entityIds);

  /**
   * Validate elements within {@link ComponentsResource#uploadComponent}
   */
  void validate(ComponentUpload componentUpload);
}
