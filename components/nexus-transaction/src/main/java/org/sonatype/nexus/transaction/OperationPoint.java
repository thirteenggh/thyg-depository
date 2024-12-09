package org.sonatype.nexus.transaction;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;
import org.aopalliance.intercept.Joinpoint;

/**
 * {@link Joinpoint} for operations; handles non-void and void return cases.
 *
 * @since 3.2
 */
final class OperationPoint<T, E extends Exception>
    implements Joinpoint
{
  private final Operation<T, E> proceed;

  private final Object self;

  private final Supplier<Method> staticPart;

  /**
   * {@link Joinpoint} for operations which return values.
   */
  OperationPoint(final Operation<T, E> operation) {
    this.proceed = operation;

    // used when logging
    this.self = operation;
    this.staticPart = lazyMethod(operation, "call");
  }

  /**
   * {@link Joinpoint} for operations which don't return values.
   */
  OperationPoint(final VoidOperation<E> operation) {
    // wrap as non-void
    this.proceed = () -> {
      operation.run();
      return null;
    };

    // used when logging
    this.self = operation;
    this.staticPart = lazyMethod(operation, "run");
  }

  @Override
  public T proceed() throws E {
    return proceed.call();
  }

  @Override
  public final Object getThis() {
    return self;
  }

  @Override
  public final AccessibleObject getStaticPart() {
    return staticPart.get();
  }

  @Override
  public String toString() {
    return self.toString();
  }

  /**
   * 'staticPart' is only used in TransactionalWrapper when trace logging,
   * so we want to defer lookup until it's needed but also cache the result
   */
  private static Supplier<Method> lazyMethod(final Object instance, final String method) {
    return Suppliers.memoize(() -> {
      try {
        return instance.getClass().getMethod(method);
      }
      catch (final NoSuchMethodException e) {
        throw new LinkageError("Missing '" + method + "' method", e);
      }
    });
  }
}
