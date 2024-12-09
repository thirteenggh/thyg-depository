package org.sonatype.nexus.common.stateguard;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;

/**
 * Simple {@link MethodInvocation} wrapper.
 * 
 * @since 3.0
 */
public class SimpleMethodInvocation
    implements MethodInvocation
{
  private final Object instance;
  private final Method method;
  private final Object[] args;

  public SimpleMethodInvocation(final Object instance, final Method method, final Object[] args) {
    this.instance = instance;
    this.method = method;
    this.args = args;
  }

  public Method getMethod() {
    return method;
  }

  public Object getThis() {
    return instance;
  }

  public Object[] getArguments() {
    return args;
  }

  public Object proceed() throws Throwable {
    try {
      return method.invoke(instance, args);
    }
    catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }

  public AccessibleObject getStaticPart() {
    throw new UnsupportedOperationException();
  }
}
