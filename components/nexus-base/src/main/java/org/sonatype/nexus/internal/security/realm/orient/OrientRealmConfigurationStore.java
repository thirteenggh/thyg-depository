package org.sonatype.nexus.internal.security.realm.orient;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.security.realm.RealmConfiguration;
import org.sonatype.nexus.security.realm.RealmConfigurationStore;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTx;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTxRetry;

/**
 * Orient {@link RealmConfigurationStore}.
 *
 * @since 3.0
 */
@Named("orient")
@Priority(Integer.MAX_VALUE)
@ManagedLifecycle(phase = SCHEMAS)
@Singleton
public class OrientRealmConfigurationStore
  extends StateGuardLifecycleSupport
  implements RealmConfigurationStore
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final OrientRealmConfigurationEntityAdapter entityAdapter;

  @Inject
  public OrientRealmConfigurationStore(@Named(DatabaseInstanceNames.SECURITY) final Provider<DatabaseInstance> databaseInstance,
                                       final OrientRealmConfigurationEntityAdapter entityAdapter)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
    this.entityAdapter = checkNotNull(entityAdapter);
  }

  @Override
  protected void doStart() {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      entityAdapter.register(db);
    }
  }

  @Override
  public RealmConfiguration newEntity() {
    return entityAdapter.newEntity();
  }

  @Override
  @Nullable
  @Guarded(by = STARTED)
  public RealmConfiguration load() {
    return inTx(databaseInstance).call(entityAdapter::get);
  }

  @Override
  @Guarded(by = STARTED)
  public void save(final RealmConfiguration configuration) {
    checkArgument(configuration instanceof OrientRealmConfiguration,
        "RealmConfiguration does not match backing store");
    inTxRetry(databaseInstance).run(db -> entityAdapter.set(db, (OrientRealmConfiguration)configuration));
  }
}
