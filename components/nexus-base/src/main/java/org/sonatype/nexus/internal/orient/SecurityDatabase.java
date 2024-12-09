package org.sonatype.nexus.internal.orient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.orient.DatabaseExternalizer;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseManager;
import org.sonatype.nexus.supportzip.SupportBundle;
import org.sonatype.nexus.supportzip.SupportBundle.ContentSource.Type;
import org.sonatype.nexus.supportzip.SupportBundleCustomizer;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Shared {@code security} database components.
 *
 * @since 3.0
 */
@SuppressWarnings("UnusedDeclaration")
public class SecurityDatabase
{
  private SecurityDatabase() {
    // empty
  }

  public static final String NAME = "security";

  /**
   * Shared {@code security} database instance provider.
   */
  @FeatureFlag(name = "nexus.orient.store.config")
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

  /**
   * Includes export of the {@code security} database in support-zip.
   */
  @FeatureFlag(name = "nexus.orient.store.config")
  @Named
  @Singleton
  public static class SupportBundleCustomizerImpl
      extends ComponentSupport
      implements SupportBundleCustomizer
  {
    private final Provider<DatabaseInstance> databaseInstance;

    @Inject
    public SupportBundleCustomizerImpl(@Named(NAME) final Provider<DatabaseInstance> databaseInstance) {
      this.databaseInstance = checkNotNull(databaseInstance);
    }

    @Override
    public void customize(final SupportBundle supportBundle) {
      String path = String.format("work/%s/%s/%s",
          DatabaseManagerImpl.WORK_PATH,
          databaseInstance.get().getName(),
          DatabaseExternalizer.EXPORT_FILENAME
      );

      supportBundle.add(new PasswordSanitizedJsonSource(Type.SECURITY, path, databaseInstance));
    }
  }
}
