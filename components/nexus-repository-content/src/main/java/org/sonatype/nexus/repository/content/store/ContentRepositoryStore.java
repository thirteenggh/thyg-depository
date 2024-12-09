package org.sonatype.nexus.repository.content.store;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.content.AttributeOperation;
import org.sonatype.nexus.repository.content.ContentRepository;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryAttributesEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryCreatedEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryDeletedEvent;
import org.sonatype.nexus.repository.content.event.repository.ContentRepositoryPreDeleteEvent;
import org.sonatype.nexus.transaction.Transactional;

import com.google.inject.assistedinject.Assisted;

import static org.sonatype.nexus.repository.content.AttributesHelper.applyAttributeChange;

/**
 * {@link ContentRepository} store.
 *
 * @since 3.21
 */
@Named
public class ContentRepositoryStore<T extends ContentRepositoryDAO>
    extends ContentStoreEventSupport<T>
{
  @Inject
  public ContentRepositoryStore(final DataSessionSupplier sessionSupplier,
                                @Assisted final String contentStoreName,
                                @Assisted final Class<T> daoClass)
  {
    super(sessionSupplier, contentStoreName, daoClass);
  }

  /**
   * Browse all content repositories in the content data store.
   */
  @Transactional
  public Collection<ContentRepository> browseContentRepositories() {
    return dao().browseContentRepositories();
  }

  /**
   * Creates the given content repository in the content data store.
   *
   * @param contentRepository the repository to create
   */
  @Transactional
  public void createContentRepository(final ContentRepositoryData contentRepository) {
    dao().createContentRepository(contentRepository);

    postCommitEvent(() -> new ContentRepositoryCreatedEvent(contentRepository));
  }

  /**
   * Retrieves a content repository from the content data store based on its config identity.
   *
   * @param configRepositoryId the config repository id
   * @return content repository if it was found
   */
  @Transactional
  public Optional<ContentRepository> readContentRepository(final EntityId configRepositoryId) {
    return dao().readContentRepository(configRepositoryId);
  }

  /**
   * Updates the attributes of the given content repository in the content data store.
   *
   * @param contentRepository the content repository to update
   */
  @Transactional
  public void updateContentRepositoryAttributes(final ContentRepository contentRepository,
                                                final AttributeOperation change,
                                                final String key,
                                                final @Nullable Object value)
  {
    // reload latest attributes, apply change, then update database if necessary
    dao().readContentRepositoryAttributes(contentRepository).ifPresent(attributes -> {
      ((ContentRepositoryData) contentRepository).setAttributes(attributes);

      if (applyAttributeChange(attributes, change, key, value)) {
        dao().updateContentRepositoryAttributes(contentRepository);

        postCommitEvent(() -> new ContentRepositoryAttributesEvent(contentRepository, change, key, value));
      }
    });
  }

  /**
   * Deletes a content repository from the content data store.
   *
   * @param contentRepository the content repository to delete
   * @return {@code true} if the content repository was deleted
   */
  @Transactional
  public boolean deleteContentRepository(final ContentRepository contentRepository) {
    preCommitEvent(() -> new ContentRepositoryPreDeleteEvent(contentRepository));
    postCommitEvent(() -> new ContentRepositoryDeletedEvent(contentRepository));

    return dao().deleteContentRepository(contentRepository);
  }

  /**
   * Deletes a content repository from the content data store based on its config identity.
   *
   * @param configRepositoryId the config repository id
   * @return {@code true} if the content repository was deleted
   */
  @Transactional
  public boolean deleteContentRepository(final EntityId configRepositoryId) {
    return dao().readContentRepository(configRepositoryId)
        .map(this::deleteContentRepository)
        .orElse(false);
  }
}
