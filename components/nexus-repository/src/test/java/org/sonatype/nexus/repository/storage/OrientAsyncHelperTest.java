package org.sonatype.nexus.repository.storage;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.storage.OrientAsyncHelper.QueueConsumingIterable;
import org.sonatype.nexus.repository.storage.OrientAsyncHelper.QueueFeedingResultListener;

import com.orientechnologies.orient.core.record.impl.ODocument;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OrientAsyncHelperTest
    extends TestSupport
{
  @Mock
  private ODocument result;

  private BlockingQueue<ODocument> newQueue(ODocument... elements) {
    return new ArrayBlockingQueue<>(Math.max(1, elements.length), false, asList(elements));
  }

  @Test
  public void testIteratorHasNext_NonEmptyQueue() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue(result));
    assertThat(underTest.hasNext(), is(true));
  }

  @Test
  public void testIteratorHasNext_EmptyQueue() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue(OrientAsyncHelper.SENTINEL));
    assertThat(underTest.hasNext(), is(false));
  }

  @Test(expected = IllegalStateException.class)
  public void testIteratorHasNext_Timeout() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue());
    underTest.hasNext();
  }

  @Test
  public void testIteratorNext_NonEmptyQueue() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue(result));
    assertThat(underTest.next(), is(result));
  }

  @Test(expected = NoSuchElementException.class)
  public void testIteratorNext_EmptyQueue() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue(OrientAsyncHelper.SENTINEL));
    underTest.next();
  }

  @Test(expected = IllegalStateException.class)
  public void testIteratorNext_Timeout() {
    QueueConsumingIterable underTest = new QueueConsumingIterable(1, newQueue());
    underTest.next();
  }

  @Test
  public void testCommandResultListenerResult_NonFullQueue() {
    BlockingQueue<ODocument> queue = newQueue();
    QueueFeedingResultListener underTest = new QueueFeedingResultListener(1, queue);
    assertThat(underTest.result(result), is(true));
    assertThat(queue.poll(), is(result));
  }

  @Test
  public void testCommandResultListenerResult_FullQueue() {
    QueueFeedingResultListener underTest = new QueueFeedingResultListener(1, newQueue(result));
    assertThat(underTest.result(result), is(false));
  }

  @Test
  public void testCommandResultListenerEnd() {
    BlockingQueue<ODocument> queue = newQueue();
    QueueFeedingResultListener underTest = new QueueFeedingResultListener(1, queue);
    underTest.end();
    assertThat(queue.poll(), is(OrientAsyncHelper.SENTINEL));
  }
}
