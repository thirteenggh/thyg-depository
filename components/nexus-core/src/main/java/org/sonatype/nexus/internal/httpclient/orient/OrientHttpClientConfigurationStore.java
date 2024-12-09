package org.sonatype.nexus.internal.httpclient.orient;

import javax.annotation.Nullable;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.httpclient.config.HttpClientConfiguration;
import org.sonatype.nexus.internal.httpclient.HttpClientConfigurationStore;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.orient.DatabaseInstanceNames;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTx;
import static org.sonatype.nexus.orient.transaction.OrientTransactional.inTxRetry;

/**
 * Orient {@link HttpClientConfigurationStore}.
 *
 * @since 3.0
 */
@Named("orient")
@Priority(Integer.MAX_VALUE)
@ManagedLifecycle(phase = SCHEMAS)
@Singleton
public class OrientHttpClientConfigurationStore
  extends StateGuardLifecycleSupport
  implements HttpClientConfigurationStore
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final OrientHttpClientConfigurationEntityAdapter entityAdapter;

  @Inject
  public OrientHttpClientConfigurationStore(@Named(DatabaseInstanceNames.CONFIG) final Provider<DatabaseInstance> databaseInstance,
                                            final OrientHttpClientConfigurationEntityAdapter entityAdapter)
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
  @Nullable
  @Guarded(by = STARTED)
  public HttpClientConfiguration load() {
    return inTx(databaseInstance).call(entityAdapter::get);
  }

  @Override
  @Guarded(by = STARTED)
  public void save(final HttpClientConfiguration configuration) {
    checkArgument(configuration instanceof OrientHttpClientConfiguration,
        "HttpClientConfiguration does not match backing store");
    inTxRetry(databaseInstance).run(db -> entityAdapter.set(db, (OrientHttpClientConfiguration)configuration));
  }

  @Override
  public HttpClientConfiguration newConfiguration() {
    return new OrientHttpClientConfiguration();
  }
}
