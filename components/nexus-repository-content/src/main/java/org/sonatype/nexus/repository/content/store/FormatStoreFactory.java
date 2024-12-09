package org.sonatype.nexus.repository.content.store;

import javax.inject.Provider;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Factory that creates content stores for a format-specific DAO.
 *
 * @since 3.26
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class FormatStoreFactory
{
  private final Provider<ContentStoreFactory> factoryProvider;

  private final Class<?> formatDaoClass;

  FormatStoreFactory(final Provider<ContentStoreFactory> factoryProvider, final Class<?> formatDaoClass) {
    this.factoryProvider = checkNotNull(factoryProvider);
    this.formatDaoClass = checkNotNull(formatDaoClass);
  }

  public <T extends ContentStoreSupport> T createFormatStore(final String contentStoreName) {
    return (T) factoryProvider.get().createContentStore(contentStoreName, formatDaoClass);
  }
}
