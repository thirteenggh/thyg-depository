package com.sonatype.nexus.ssl.plugin.internal.keystore.orient;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.sonatype.nexus.ssl.plugin.internal.keystore.KeyStoreManagerImpl;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.ssl.spi.KeyStoreStorage;
import org.sonatype.nexus.ssl.spi.KeyStoreStorageManager;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTx;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTxRetry;

/**
 * Implementation of {@link KeyStoreStorageManager} for the SSL trust store. Uses OrientDB as backing storage to
 * facilitate distribution of data across cluster.
 * 
 * @since 3.1
 */
@Named(KeyStoreManagerImpl.NAME)
@Priority(Integer.MAX_VALUE)
@ManagedLifecycle(phase = SCHEMAS)
@Singleton
public class OrientKeyStoreStorageManager
    extends StateGuardLifecycleSupport
    implements KeyStoreStorageManager
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final OrientKeyStoreDataEntityAdapter entityAdapter;

  private final EventManager eventManager;

  private final Collection<OrientKeyStoreStorage> storages = new ConcurrentLinkedQueue<>();

  @Inject
  public OrientKeyStoreStorageManager(@Named(DatabaseInstanceNames.CONFIG) final Provider<DatabaseInstance> databaseInstance,
                                    final OrientKeyStoreDataEntityAdapter entityAdapter,
                                    final EventManager eventManager)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
    this.entityAdapter = checkNotNull(entityAdapter);
    this.eventManager = checkNotNull(eventManager);
  }

  @Override
  protected void doStart() throws Exception {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      entityAdapter.register(db);
    }
  }

  @Override
  protected void doStop() throws Exception {
    storages.forEach(eventManager::unregister);
    storages.clear();
  }

  @Override
  @Guarded(by = STARTED)
  public KeyStoreStorage createStorage(final String keyStoreName) {
    checkNotNull(keyStoreName);
    OrientKeyStoreStorage storage = new OrientKeyStoreStorage(this, KeyStoreManagerImpl.NAME + '/' + keyStoreName);
    eventManager.register(storage);
    storages.add(storage);
    return storage;
  }

  @Guarded(by = STARTED)
  @Nullable
  public OrientKeyStoreData load(final String keyStoreName) {
    checkNotNull(keyStoreName);
    return inTx(databaseInstance).call(db -> entityAdapter.load(db, keyStoreName));
  }

  @Guarded(by = STARTED)
  public void save(final OrientKeyStoreData entity) {
    checkNotNull(entity);
    inTxRetry(databaseInstance).run(db -> entityAdapter.save(db, entity));
  }
}
