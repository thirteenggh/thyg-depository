package org.sonatype.nexus.common.stateguard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.sonatype.goodies.common.ComponentSupport;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * {@link Guarded} interceptor.
 *
 * @since 3.0
 */
public class GuardedInterceptor
    extends ComponentSupport
    implements MethodInterceptor
{
  @Override
  public Object invoke(final MethodInvocation invocation) throws Throwable {
    checkNotNull(invocation);

    Object target = invocation.getThis();
    Method method = invocation.getMethod();

    checkState(target instanceof StateGuardAware, "Invocation target (%s) does not implement: %s",
        target.getClass(), StateGuardAware.class);
    StateGuard states = ((StateGuardAware) target).getStateGuard();
    checkState(states != null);

    Guarded config = method.getAnnotation(Guarded.class);
    checkState(config != null);
    Guard guard = states.guard(config.by());

    log.trace("Invoking: {} -> {}", guard, method);

    try {
      return guard.run(new MethodInvocationAction(invocation));
    }
    catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }
}
