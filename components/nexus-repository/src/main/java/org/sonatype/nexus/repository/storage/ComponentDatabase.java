package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Shared {@code component} database components.
 *
 * @since 3.0
 */
public class ComponentDatabase
{
  private ComponentDatabase() {
    // empty
  }

  public static final String NAME = "component";

  /**
   * Shared {@code component} database instance provider.
   */
  @Named(NAME)
  @Singleton
  public static class ProviderImpl
      implements Provider<DatabaseInstance>
  {
    private final DatabaseManager databaseManager;

    @Inject
    public ProviderImpl(final DatabaseManager databaseManager) {
      this.databaseManager = checkNotNull(databaseManager);
    }

    @Override
    public DatabaseInstance get() {
      return databaseManager.instance(NAME);
    }
  }
}
