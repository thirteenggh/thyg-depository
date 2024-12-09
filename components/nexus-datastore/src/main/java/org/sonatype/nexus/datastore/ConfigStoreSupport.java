package org.sonatype.nexus.datastore;

import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.datastore.api.DataAccess;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.transaction.TransactionalStore;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.google.inject.TypeLiteral;
import org.eclipse.sisu.inject.TypeArguments;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.datastore.api.DataStoreManager.CONFIG_DATASTORE_NAME;

/**
 * Support class for transactional domain stores backed by the config data store.
 *
 * @since 3.21
 */
public abstract class ConfigStoreSupport<T extends DataAccess>
    extends StateGuardLifecycleSupport
    implements TransactionalStore<DataSession<?>>
{
  private final DataSessionSupplier sessionSupplier;

  private final Class<T> daoClass;

  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected ConfigStoreSupport(final DataSessionSupplier sessionSupplier) {
    this.sessionSupplier = checkNotNull(sessionSupplier);

    // use generic type information to discover the DAO class from the concrete implementation
    TypeLiteral<?> superType = TypeLiteral.get(getClass()).getSupertype(ConfigStoreSupport.class);
    this.daoClass = (Class) TypeArguments.get(superType, 0).getRawType();
  }

  // alternative constructor that overrides discovery of the DAO class
  protected ConfigStoreSupport(final DataSessionSupplier sessionSupplier, final Class<T> daoClass) {
    this.sessionSupplier = checkNotNull(sessionSupplier);
    this.daoClass = checkNotNull(daoClass);
  }

  protected DataSession<?> thisSession() {
    return UnitOfWork.currentSession();
  }

  protected T dao() {
    return thisSession().access(daoClass);
  }

  @Override
  public DataSession<?> openSession() {
    return sessionSupplier.openSession(CONFIG_DATASTORE_NAME);
  }
}
