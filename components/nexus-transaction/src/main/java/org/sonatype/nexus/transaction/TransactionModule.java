package org.sonatype.nexus.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.sonatype.nexus.common.guice.AbstractInterceptorModule;

import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matchers;

/**
 * Registers transactional method behaviour with Guice.
 *
 * @since 3.0
 */
public class TransactionModule
    extends AbstractInterceptorModule
{
  @Override
  protected void configure() {
    bindInterceptor(Matchers.any(), new TransactionalMatcher(), new TransactionInterceptor());
  }

  private static final class TransactionalMatcher
      extends AbstractMatcher<Method>
  {
    @Override
    public boolean matches(final Method method) {
      if (method.isAnnotationPresent(Transactional.class)) {
        return true;
      }
      // look for stereotypes; annotations marked with @Transactional
      for (final Annotation ann : method.getDeclaredAnnotations()) {
        if (ann.annotationType().isAnnotationPresent(Transactional.class)) {
          return true;
        }
      }
      return false;
    }
  }
}
