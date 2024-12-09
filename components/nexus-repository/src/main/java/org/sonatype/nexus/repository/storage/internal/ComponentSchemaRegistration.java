package org.sonatype.nexus.repository.storage.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.orient.DatabaseInstance;
import org.sonatype.nexus.repository.browse.internal.orient.BrowseNodeEntityAdapter;
import org.sonatype.nexus.repository.storage.AssetEntityAdapter;
import org.sonatype.nexus.repository.storage.BucketEntityAdapter;
import org.sonatype.nexus.repository.storage.ComponentDatabase;
import org.sonatype.nexus.repository.storage.ComponentEntityAdapter;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.SCHEMAS;

/**
 * Registers schemas used for component storage.
 *
 * @since 3.2.1
 */
@Named
@ManagedLifecycle(phase = SCHEMAS)
@Singleton
public class ComponentSchemaRegistration
    extends LifecycleSupport
{
  private final Provider<DatabaseInstance> databaseInstance;

  private final BucketEntityAdapter bucketEntityAdapter;

  private final ComponentEntityAdapter componentEntityAdapter;

  private final AssetEntityAdapter assetEntityAdapter;

  private final BrowseNodeEntityAdapter browseNodeEntityAdapter;

  @Inject
  public ComponentSchemaRegistration(@Named(ComponentDatabase.NAME) final Provider<DatabaseInstance> databaseInstance,
                                     final BucketEntityAdapter bucketEntityAdapter,
                                     final ComponentEntityAdapter componentEntityAdapter,
                                     final AssetEntityAdapter assetEntityAdapter,
                                     final BrowseNodeEntityAdapter browseNodeEntityAdapter)
  {
    this.databaseInstance = checkNotNull(databaseInstance);

    this.bucketEntityAdapter = checkNotNull(bucketEntityAdapter);
    this.componentEntityAdapter = checkNotNull(componentEntityAdapter);
    this.assetEntityAdapter = checkNotNull(assetEntityAdapter);
    this.browseNodeEntityAdapter = checkNotNull(browseNodeEntityAdapter);
  }

  @Override
  protected void doStart() throws Exception {
    try (ODatabaseDocumentTx db = databaseInstance.get().connect()) {
      bucketEntityAdapter.register(db);
      componentEntityAdapter.register(db);
      assetEntityAdapter.register(db);
      browseNodeEntityAdapter.register(db);
    }
  }
}
