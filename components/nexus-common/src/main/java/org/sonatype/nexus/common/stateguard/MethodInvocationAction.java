package org.sonatype.nexus.common.stateguard;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.aopalliance.intercept.MethodInvocation;

/**
 * Adapts {@link MethodInvocation} to {@link Action}.
 *
 * @since 3.0
 */
class MethodInvocationAction
    implements Action<Object>
{
  private final MethodInvocation invocation;

  public MethodInvocationAction(final MethodInvocation invocation) {
    this.invocation = invocation;
  }

  @Nullable
  @Override
  public Object run() throws Exception {
    try {
      return invocation.proceed();
    }
    catch (Exception e) {
      throw e;
    }
    catch (Throwable e) {
      throw new InvocationTargetException(e);
    }
  }
}
