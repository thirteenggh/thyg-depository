package org.sonatype.nexus.repository.content.store;

import java.util.Collection;
import java.util.Optional;

import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.datastore.api.ContentDataAccess;
import org.sonatype.nexus.datastore.api.SchemaTemplate;
import org.sonatype.nexus.repository.content.ContentRepository;

import org.apache.ibatis.annotations.Param;

/**
 * Content repository {@link ContentDataAccess}.
 *
 * @since 3.20
 */
@SchemaTemplate("format")
public interface ContentRepositoryDAO
    extends ContentDataAccess
{
  /**
   * Browse all content repositories in the content data store.
   */
  Collection<ContentRepository> browseContentRepositories();

  /**
   * Creates the given content repository in the content data store.
   *
   * @param contentRepository the repository to create
   */
  void createContentRepository(ContentRepositoryData contentRepository);

  /**
   * Retrieves a content repository from the content data store based on its config identity.
   *
   * @param configRepositoryId the config repository id
   * @return content repository if it was found
   */
  Optional<ContentRepository> readContentRepository(@Param("configRepositoryId") EntityId configRepositoryId);

  /**
   * Retrieves the latest attributes of the given content repository in the content data store.
   *
   * @param contentRepository the content repository to read
   * @return repository attributes if found
   */
  Optional<NestedAttributesMap> readContentRepositoryAttributes(ContentRepository contentRepository);

  /**
   * Updates the attributes of the given content repository in the content data store.
   *
   * @param contentRepository the content repository to update
   */
  void updateContentRepositoryAttributes(ContentRepository contentRepository);

  /**
   * Deletes a content repository from the content data store.
   *
   * @param contentRepository the content repository to delete
   * @return {@code true} if the content repository was deleted
   */
  boolean deleteContentRepository(ContentRepository contentRepository);
}
