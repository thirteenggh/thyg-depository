package org.sonatype.nexus.orient.entity;

import com.orientechnologies.orient.core.command.OCommandExecutor;
import com.orientechnologies.orient.core.command.OCommandRequestText;
import com.orientechnologies.orient.core.db.ODatabase;
import com.orientechnologies.orient.core.db.ODatabaseInternal;
import com.orientechnologies.orient.core.db.ODatabaseLifecycleListener;
import com.orientechnologies.orient.core.db.ODatabaseListener;
import com.orientechnologies.orient.core.hook.ORecordHook;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.record.ORecord;
import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Skeleton database listener that does nothing.
 * 
 * @since 3.1
 */
@SuppressWarnings("rawtypes")
public class DatabaseListenerSupport
    implements ODatabaseLifecycleListener, ODatabaseListener, ORecordHook.Scoped
{
  // ODatabaseLifecycleListener

  @Override
  public PRIORITY getPriority() {
    return PRIORITY.LAST;
  }

  @Override
  public void onCreate(final ODatabaseInternal db) {
    // no-op
  }

  @Override
  public void onDrop(final ODatabaseInternal db) {
    // no-op
  }

  @Override
  public void onOpen(final ODatabaseInternal db) {
    // no-op
  }

  @Override
  public void onClose(final ODatabaseInternal db) {
    // no-op
  }

  @Override
  public void onCreateClass(final ODatabaseInternal db, final OClass type) {
    // no-op
  }

  @Override
  public void onDropClass(final ODatabaseInternal db, final OClass type) {
    // no-op
  }

  @Override
  public void onLocalNodeConfigurationRequest(final ODocument config) {
    // no-op
  }

  // ODatabaseListener

  @Override
  public void onCreate(final ODatabase db) {
    // no-op
  }

  @Override
  public void onDelete(final ODatabase db) {
    // no-op
  }

  @Override
  public void onOpen(final ODatabase db) {
    // no-op
  }

  @Override
  public void onClose(final ODatabase db) {
    // no-op
  }

  @Override
  public void onBeforeTxBegin(final ODatabase db) {
    // no-op
  }

  @Override
  public void onBeforeTxCommit(final ODatabase db) {
    // no-op
  }

  @Override
  public void onAfterTxCommit(ODatabase iDatabase) {
    // no-op
  }

  @Override
  public void onBeforeTxRollback(final ODatabase db) {
    // no-op
  }

  @Override
  public void onAfterTxRollback(ODatabase iDatabase) {
    // no-op
  }

  @Override
  public void onBeforeCommand(final OCommandRequestText cmd, final OCommandExecutor executor) {
    // no-op
  }

  @Override
  public void onAfterCommand(final OCommandRequestText cmd, final OCommandExecutor executor, final Object result) {
    // no-op
  }

  @Override
  public boolean onCorruptionRepairDatabase(final ODatabase db, final String reason, final String action) {
    return false;
  }

  // ORecordHook

  @Override
  public DISTRIBUTED_EXECUTION_MODE getDistributedExecutionMode() {
    return DISTRIBUTED_EXECUTION_MODE.BOTH;
  }

  @Override
  public RESULT onTrigger(final TYPE type, final ORecord record) {
    return RESULT.RECORD_NOT_CHANGED;
  }

  @Override
  public void onUnregister() {
    // no-op
  }

  // ORecordHook.Scoped

  private static final SCOPE[] SCOPES = { SCOPE.CREATE, SCOPE.UPDATE, SCOPE.DELETE };

  @Override
  public SCOPE[] getScopes() {
    return SCOPES;
  }
}
