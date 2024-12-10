package org.sonatype.nexus.common.guice;

import java.lang.reflect.Method;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.matcher.Matcher;
import com.google.inject.name.Names;
import org.aopalliance.intercept.MethodInterceptor;

public abstract class AbstractInterceptorModule
    extends AbstractModule
{
  private boolean bound;

  @Override
  protected void bindInterceptor(final Matcher<? super Class<?>> classMatcher,
                                 final Matcher<? super Method> methodMatcher,
                                 final MethodInterceptor... interceptors)
  {
    if (!bound) {
      // Explicitly bind module instance under a specific sub-type (not Module as Guice forbids that)
      bind(Key.get(AbstractInterceptorModule.class, Names.named(getClass().getName()))).toInstance(this);
      bound = true;
    }
    super.bindInterceptor(classMatcher, methodMatcher, interceptors);
  }
}
