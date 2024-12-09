package org.sonatype.nexus.transaction;

import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.base.Suppliers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.transaction.Transactional.DEFAULT_REASON;

/**
 * Test operations behaviour.
 */
@SuppressWarnings("boxing")
public class OperationsTest
    extends TestSupport
{
  ExampleMethods methods = new ExampleMethods(new ExampleMethods.ExampleNestedStore());

  @Mock
  TransactionalSession<Transaction> session;

  @Mock
  Transaction tx;

  @Before
  public void setUp() {
    when(session.getTransaction()).thenReturn(tx);
    UnitOfWork.begin(Suppliers.ofInstance(session));
  }

  @After
  public void tearDown() {
    UnitOfWork.end();
  }

  @Test
  public void testDefaultSpec() throws Exception {

    Transactional.operation.call(() -> methods.nonTransactional());

    InOrder order = inOrder(session, tx);
    order.verify(session).getTransaction();
    order.verify(tx).reason(DEFAULT_REASON);
    order.verify(tx).begin();
    order.verify(tx).commit();
    order.verify(session).close();
    verifyNoMoreInteractions(session, tx);
  }

  @Test
  public void testRetrySuccess() throws Exception {
    when(tx.allowRetry(any(Exception.class))).thenReturn(true);

    methods.setCountdownToSuccess(3);
    Transactional.operation
        .retryOn(IOException.class)
        .throwing(IOException.class)
        .call(() -> methods.retryOnCheckedException());

    InOrder order = inOrder(session, tx);
    order.verify(session).getTransaction();
    order.verify(tx).reason(DEFAULT_REASON);
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).commit();
    order.verify(session).close();
    verifyNoMoreInteractions(session, tx);
  }

  @Test(expected = IOException.class)
  public void testRetryFailure() throws Exception {
    when(tx.allowRetry(any(Exception.class))).thenReturn(true).thenReturn(false);

    methods.setCountdownToSuccess(100);
    try {
      Transactional.operation
          .retryOn(IOException.class)
          .throwing(IOException.class)
          .call(() -> methods.retryOnCheckedException());
    }
    finally {
      InOrder order = inOrder(session, tx);
      order.verify(session).getTransaction();
      order.verify(tx).reason(DEFAULT_REASON);
      order.verify(tx).begin();
      order.verify(tx).rollback();
      order.verify(tx).allowRetry(any(IOException.class));
      order.verify(tx).begin();
      order.verify(tx).rollback();
      order.verify(tx).allowRetry(any(IOException.class));
      order.verify(session).close();
      verifyNoMoreInteractions(session, tx);
    }
  }

  @Test
  public void testCanUseStereotypeAnnotation() throws Exception {
    when(tx.allowRetry(any(Exception.class))).thenReturn(true);

    methods.setCountdownToSuccess(3);
    Transactional.operation
        .stereotype(RetryOnIOException.class)
        .throwing(IOException.class)
        .call(() -> methods.retryOnCheckedException());

    InOrder order = inOrder(session, tx);
    order.verify(session).getTransaction();
    order.verify(tx).reason(DEFAULT_REASON);
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).rollback();
    order.verify(tx).allowRetry(any(IOException.class));
    order.verify(tx).begin();
    order.verify(tx).commit();
    order.verify(session).close();
    verifyNoMoreInteractions(session, tx);
  }

  @Test
  public void testBatchModeDoesntLeakOutsideScope() {
    final Transaction[] txHolder = new Transaction[2];

    UnitOfWork.begin(() -> newMockSession());
    try {
      UnitOfWork.beginBatch(() -> newMockSession());
      try {
        Transactional.operation.run(() -> txHolder[0] = UnitOfWork.currentTx());
        Transactional.operation.run(() -> txHolder[1] = UnitOfWork.currentTx());

        // batched: transactions should be same
        assertThat(txHolder[0], is(txHolder[1]));
      }
      finally {
        UnitOfWork.end(); // ends inner-batch-work
      }

      Transactional.operation.run(() -> txHolder[0] = UnitOfWork.currentTx());
      Transactional.operation.run(() -> txHolder[1] = UnitOfWork.currentTx());

      // non-batched: transactions should differ
      assertThat(txHolder[0], is(not(txHolder[1])));
    }
    finally {
      UnitOfWork.end(); // ends outer-non-batch-work
    }
  }

  private TransactionalSession<Transaction> newMockSession() {
    TransactionalSession<Transaction> session = mock(TransactionalSession.class);
    when(session.getTransaction()).thenReturn(mock(Transaction.class));
    return session;
  }
}
