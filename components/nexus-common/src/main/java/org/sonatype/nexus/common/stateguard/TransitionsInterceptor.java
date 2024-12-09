package org.sonatype.nexus.common.stateguard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.sonatype.goodies.common.ComponentSupport;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * {@link Transitions} interceptor.
 *
 * @since 3.0
 */
public class TransitionsInterceptor
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

    Transitions config = method.getAnnotation(Transitions.class);
    checkState(config != null);
    Transition transition = states.transition(config.to(), config.silent(), config.ignore());
    if (config.from() != null && config.from().length != 0) {
      transition = transition.from(config.from());
    }

    log.trace("Invoking: {} -> {}", transition, method);

    try {
      return transition.run(new MethodInvocationAction(invocation));
    }
    catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }
}
