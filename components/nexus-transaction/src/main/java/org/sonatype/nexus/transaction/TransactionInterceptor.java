package org.sonatype.nexus.transaction;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.sonatype.goodies.common.ComponentSupport;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import static org.sonatype.nexus.transaction.UnitOfWork.peekTransaction;
import static org.sonatype.nexus.transaction.UnitOfWork.openSession;

/**
 * Opens a transaction when entering a transactional method and closes it on exit.
 * Nested transactional methods proceed as normal inside the current transaction.
 *
 * @since 3.0
 */
final class TransactionInterceptor
    extends ComponentSupport
    implements MethodInterceptor
{
  @Override
  public Object invoke(final MethodInvocation mi) throws Throwable {
    TransactionalStore<?> store = null;
    if (mi.getThis() instanceof TransactionalStore<?>) {
      store = (TransactionalStore<?>) mi.getThis();
    }

    Transaction tx = peekTransaction();
    if (tx != null) { // nested transactional session
      if (store != null) {
        tx.capture(store);
      }
      if (tx.isActive()) {
        return mi.proceed(); // no need to wrap active transaction
      }
      return proceedWithTransaction(mi, tx);
    }

    try (TransactionalSession<?> session = openSession(store)) {
      return proceedWithTransaction(mi, session.getTransaction());
    }
  }

  private Object proceedWithTransaction(final MethodInvocation mi, final Transaction tx) throws Throwable {

    Method method = mi.getMethod();
    Transactional spec = findSpec(method);

    log.trace("Invoking: {} -> {}", spec, method);

    return new TransactionalWrapper(spec, mi).proceedWithTransaction(tx);
  }

  private static final Transactional findSpec(final Method method) {
    Transactional spec = method.getAnnotation(Transactional.class);
    if (spec != null) {
      return spec;
    }
    // look for stereotypes; annotations marked with @Transactional
    for (final Annotation ann : method.getDeclaredAnnotations()) {
      spec = ann.annotationType().getAnnotation(Transactional.class);
      if (spec != null) {
        return spec;
      }
    }
    throw new IllegalStateException("Missing @Transactional on: " + method);
  }
}
