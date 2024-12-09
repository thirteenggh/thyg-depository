package org.sonatype.nexus.webhooks

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.event.EventManager

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify

/**
 * Tests for {@link Webhook}.
 */
class WebhookTest
  extends TestSupport
{
  @Mock
  private EventManager eventManager

  /**
   * Test {@link WebhookType}.
   */
  private static class TestWebhookType
    extends WebhookType
  {
    TestWebhookType() {
      super('test')
    }
  }

  /**
   * Test {@link Webhook}.
   */
  private static class TestWebhook
    extends Webhook
  {
    @Override
    WebhookType getType() {
      return new TestWebhookType()
    }

    @Override
    String getName() {
      return 'test'
    }

    // promote to public
    @Override
    Set<WebhookSubscription> getSubscriptions() {
      return super.getSubscriptions()
    }
  }

  private TestWebhook underTest

  @Before
  void setUp() {
    underTest = new TestWebhook(
        eventManager: eventManager
    )
  }

  @Test
  void 'prepends rm: to event id'() {
    assert underTest.id.startsWith("rm:")
  }

  @Test
  void 'listen for events on first subscription'() {
    assert underTest.subscriptions.size() == 0

    def subscription1 = underTest.subscribe(mock(WebhookConfiguration.class))
    assert underTest.subscriptions.size() == 1

    def subscription2 = underTest.subscribe(mock(WebhookConfiguration.class))
    assert underTest.subscriptions.size() == 2

    verify(eventManager, times(1)).register(underTest)
  }

  @Test
  void 'stop listening for events when subscriptions empty'() {
    def subscription1 = underTest.subscribe(mock(WebhookConfiguration.class))
    def subscription2 = underTest.subscribe(mock(WebhookConfiguration.class))

    subscription1.cancel()
    assert underTest.subscriptions.size() == 1

    subscription2.cancel()
    assert underTest.subscriptions.size() == 0

    verify(eventManager, times(1)).unregister(underTest)
  }
}
