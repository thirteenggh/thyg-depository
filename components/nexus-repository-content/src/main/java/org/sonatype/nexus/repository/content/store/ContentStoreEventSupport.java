package org.sonatype.nexus.repository.content.store;

import java.util.Optional;
import java.util.function.Supplier;

import javax.inject.Inject;

import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.datastore.api.ContentDataAccess;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.datastore.api.SchemaTemplate;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacetFinder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.stream;
import static org.sonatype.nexus.common.text.Strings2.lower;

/**
 * Support class for format content stores that send events.
 *
 * @since 3.27
 */
public abstract class ContentStoreEventSupport<T extends ContentDataAccess>
    extends ContentStoreSupport<T>
{
  private final String format;

  private ContentFacetFinder contentFacetFinder;

  private EventManager eventManager;

  protected ContentStoreEventSupport(final DataSessionSupplier sessionSupplier,
                                     final String contentStoreName,
                                     final Class<T> daoClass)
  {
    super(sessionSupplier, contentStoreName, daoClass);
    this.format = extractFormat(daoClass);
  }

  @Inject
  protected void setDependencies(final ContentFacetFinder contentFacetFinder, final EventManager eventManager) {
    this.contentFacetFinder = checkNotNull(contentFacetFinder);
    this.eventManager = checkNotNull(eventManager);
  }

  public void preCommitEvent(final Supplier<ContentStoreEvent> eventSupplier) {
    thisSession().preCommit(() -> postEvent(eventSupplier));
  }

  public void postCommitEvent(final Supplier<ContentStoreEvent> eventSupplier) {
    thisSession().postCommit(() -> postEvent(eventSupplier));
  }

  private void postEvent(final Supplier<ContentStoreEvent> eventSupplier) {
    ContentStoreEvent event = eventSupplier.get();
    Optional<Repository> repository = contentFacetFinder.findRepository(format, event.contentRepositoryId);
    if (repository.isPresent()) {
      event.setRepository(repository.get());
      eventManager.post(event);
    }
    else {
      log.warn("Could not find repository for {}, ignoring event", event);
    }
  }

  /**
   * Returns the lower-case form of the format after removing baseName from daoName.
   */
  private String extractFormat(final Class<T> daoClass) {
    // assume only one level between format and base DAO
    String formatDao = daoClass.getSimpleName();
    String dao = stream(daoClass.getInterfaces())
        .filter(c -> c.isAnnotationPresent(SchemaTemplate.class))
        .map(Class::getSimpleName)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Cannot determine format"));

    String prefix = lower(formatDao.substring(0, formatDao.length() - dao.length()));
    checkArgument(!prefix.isEmpty(), "%s must add a prefix to %s", formatDao, dao);
    return prefix;
  }
}
