package org.sonatype.nexus.datastore.mybatis;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * MyBatis {@link Interceptor} that wraps any new {@link Executor} with {@link EntityExecutor}.
 *
 * @since 3.19
 */
final class EntityInterceptor
    implements Interceptor
{
  private final AtomicBoolean frozenMarker;

  public EntityInterceptor(final AtomicBoolean frozenMarker) {
    this.frozenMarker = checkNotNull(frozenMarker);
  }

  @Override
  public Object plugin(final Object delegate) {
    if (delegate instanceof Executor) {
      return new EntityExecutor((Executor) delegate, frozenMarker);
    }
    return delegate;
  }

  @Override
  public Object intercept(final Invocation invocation) {
    throw new UnsupportedOperationException("unused");
  }
}
