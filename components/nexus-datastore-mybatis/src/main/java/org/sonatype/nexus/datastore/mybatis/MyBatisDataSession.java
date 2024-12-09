package org.sonatype.nexus.datastore.mybatis;

import java.util.List;

import org.sonatype.nexus.datastore.api.DataAccess;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.transaction.Transaction;
import org.sonatype.nexus.transaction.TransactionSupport;

import org.apache.ibatis.session.SqlSession;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;

/**
 * MyBatis {@link DataSession}.
 *
 * @since 3.19
 */
public final class MyBatisDataSession
    extends TransactionSupport
    implements DataSession<Transaction>
{
  private final SqlSession session;

  private Object preCommit;

  private Object postCommit;

  private Object onRollback;

  public MyBatisDataSession(final SqlSession session) {
    this.session = checkNotNull(session);
  }

  @Override
  public <D extends DataAccess> D access(final Class<D> type) {
    return session.getMapper(type);
  }

  @Override
  public Transaction getTransaction() {
    return this;
  }

  @Override
  public void preCommit(final Runnable hook) {
    preCommit = addHook(preCommit, hook);
  }

  @Override
  public void postCommit(final Runnable hook) {
    postCommit = addHook(postCommit, hook);
  }

  @Override
  public void onRollback(final Runnable hook) {
    onRollback = addHook(onRollback, hook);
  }

  @Override
  public String sqlDialect() {
    return session.getConfiguration().getDatabaseId();
  }

  @Override
  protected void doCommit() {
    callHooks(preCommit);
    session.commit();
    callHooks(postCommit);
  }

  @Override
  protected void doRollback() {
    session.rollback();
    callHooks(onRollback);
  }

  @Override
  public boolean allowRetry(final Exception cause) {
    boolean retry = super.allowRetry(cause);
    if (retry) {
      clearHooks(); // avoid duplicate hooks from stacking up on retry
    }
    return retry;
  }

  @Override
  public void close() {
    clearHooks();
    session.close();
  }

  @SuppressWarnings("unchecked")
  private Object addHook(final Object hooks, final Runnable hook) {
    if (hooks == null) {
      return hook;
    }
    else if (hooks instanceof Runnable) {
      return newArrayList(hooks, hook);
    }
    else {
      ((List<Runnable>) hooks).add(hook);
      return hooks;
    }
  }

  @SuppressWarnings("unchecked")
  private void callHooks(final Object hooks) {
    if (hooks == null) {
      // nothing to call
    }
    else if (hooks instanceof Runnable) {
      ((Runnable) hooks).run();
    }
    else {
      ((List<Runnable>) hooks).forEach(Runnable::run);
    }
  }

  private void clearHooks() {
    preCommit = null;
    postCommit = null;
    onRollback = null;
  }
}
