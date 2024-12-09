package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.common.stateguard.Guarded;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.orient.DatabaseInstance;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;
import static org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport.State.STARTED;

/**
 * @since 3.6
 */
@Singleton
@ManagedLifecycle(phase = SCHEMAS)
@Named
public class BucketStoreImpl
    extends StateGuardLifecycleSupport
    implements BucketStore, Lifecycle
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final BucketEntityAdapter entityAdapter;

  @Inject
  public BucketStoreImpl(@Named("component") final Provider<DatabaseInstance> databaseInstance,
                         final BucketEntityAdapter entityAdapter)
  {
    this.databaseInstance = checkNotNull(databaseInstance);
    this.entityAdapter = checkNotNull(entityAdapter);
  }

  @Override
  @Guarded(by = STARTED)
  public Bucket read(final String repositoryName)
  {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      return entityAdapter.read(db, repositoryName);
    }
  }

  @Override
  public Bucket getById(final EntityId bucketId) {
    try (ODatabaseDocumentTx db = databaseInstance.get().acquire()) {
      return entityAdapter.read(db, bucketId);
    }
  }
}
