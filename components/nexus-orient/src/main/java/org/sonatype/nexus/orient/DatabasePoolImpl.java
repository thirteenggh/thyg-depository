package org.sonatype.nexus.orient;

import com.orientechnologies.orient.core.db.OPartitionedDatabasePool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.storage.OStorage;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link DatabasePool} implementation.
 *
 * @since 3.0
 */
public class DatabasePoolImpl
  extends DatabasePoolSupport
{
  private final OPartitionedDatabasePool delegate;

  public DatabasePoolImpl(final OPartitionedDatabasePool delegate, final String name) {
    super(name);
    this.delegate = checkNotNull(delegate);
  }

  @Override
  protected void doStop() throws Exception {
    delegate.close();
  }

  @Override
  public ODatabaseDocumentTx acquire() {
    ensureStarted();
    return delegate.acquire();
  }

  @Override
  public int getAvailableCount() {
    return delegate.getAvailableConnections();
  }

  @Override
  public int getPoolSize() {
    return delegate.getCreatedInstances();
  }

  @Override
  public void replaceStorage(final OStorage storage) {
    replaceStorage(delegate, storage);
  }
}
