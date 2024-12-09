package org.sonatype.nexus.validation.internal;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.executable.ExecutableValidator;

import org.sonatype.nexus.validation.Validate;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link MethodInterceptor} that validates method arguments and return values.
 * 
 * @since 3.0
 */
public class ValidationInterceptor
    implements MethodInterceptor
{
  @Inject
  private ExecutableValidator methodValidator;

  public Object invoke(final MethodInvocation mi) throws Throwable {
    checkNotNull(methodValidator);

    final ClassLoader tccl = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(HibernateValidator.class.getClassLoader());
      final Validate validate = mi.getMethod().getAnnotation(Validate.class);

      validateParameters(mi.getThis(), mi.getMethod(), mi.getArguments(), validate.groups());

      final Object result = mi.proceed();

      validateReturnValue(mi.getThis(), mi.getMethod(), result, validate.groups());

      return result;
    }
    finally {
      Thread.currentThread().setContextClassLoader(tccl);
    }
  }

  private void validateParameters(final Object obj, final Method method, final Object[] args, final Class<?>[] groups) {
    final Set<ConstraintViolation<Object>> violations = methodValidator.validateParameters(obj, method, args, groups);
    if (!violations.isEmpty()) {
      final String message = "Invalid arguments calling '" + method + "' with " + Arrays.deepToString(args);
      throw new ConstraintViolationException(message, violations);
    }
  }

  private void validateReturnValue(final Object obj, final Method method, final Object value, final Class<?>[] groups) {
    final Set<ConstraintViolation<Object>> violations = methodValidator.validateReturnValue(obj, method, value, groups);
    if (!violations.isEmpty()) {
      final String message = "Invalid value returned by '" + method + "' was " + value;
      throw new ConstraintViolationException(message, violations);
    }
  }
}
