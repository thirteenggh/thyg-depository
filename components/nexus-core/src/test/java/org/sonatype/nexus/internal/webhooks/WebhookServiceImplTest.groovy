package org.sonatype.nexus.internal.webhooks

import java.text.SimpleDateFormat

import javax.inject.Provider

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.webhooks.GlobalRepositoryWebhook
import org.sonatype.nexus.repository.webhooks.GlobalRepositoryWebhook.RepositoryWebhookPayload
import org.sonatype.nexus.repository.webhooks.GlobalRepositoryWebhook.RepositoryWebhookPayload.Repository
import org.sonatype.nexus.webhooks.WebhookRequest

import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.util.EntityUtils
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests {@link WebhookServiceImpl}
 */
class WebhookServiceImplTest
    extends TestSupport
{
  @Mock
  Provider<CloseableHttpClient> httpClientProvider

  @Mock
  CloseableHttpClient httpClient

  private ArgumentCaptor<HttpPost> postCaptor

  private WebhookServiceImpl underTest

  @Before
  void setup() {
    underTest = new WebhookServiceImpl(
        httpClientProvider: httpClientProvider
    )
    when(httpClientProvider.get()).thenReturn(httpClient)

    postCaptor = ArgumentCaptor.forClass(HttpPost.class)
    def response = mock(CloseableHttpResponse.class)
    def statusLine = mock(StatusLine.class)
    when(statusLine.getStatusCode()).thenReturn(200)
    when(response.getStatusLine()).thenReturn(statusLine)
    when(httpClient.execute(postCaptor.capture())).thenReturn(response)
  }

  @Test
  void 'Properly marshalls and signs payload'() {
    def request = new WebhookRequest()

    def expectedDate = '2016-08-18T18:18:30.326+0000'
    def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
    def timestamp = dateFormat.parse(expectedDate)

    request.url = new URI('uri')
    request.secret = 'secret'
    request.webhook = new GlobalRepositoryWebhook()
    def payload = new RepositoryWebhookPayload(
        nodeId: 'nodeId',
        timestamp: timestamp,
        initiator: 'initiator',
        action: 'CREATED'
    )
    payload.repository = new Repository(
        format: 'format',
        name: 'name',
        type: 'value'
    )
    request.payload = payload

    underTest.send(request)

    def post = postCaptor.getValue()

    def expectedPayload = '{\"timestamp\":\"2016-08-18T18:18:30.326+00:00\",\"nodeId\":\"nodeId\",' +
        '\"initiator\":\"initiator\",\"action\":\"CREATED\",\"repository\":{\"format\":\"format\",\"name\":\"name\",' +
        '\"type\":\"value\"}}'
    assertThat(EntityUtils.toString(post.entity), equalTo(expectedPayload))
    assertThat(post.getFirstHeader('X-Nexus-Webhook-Signature').value,
        equalTo('918cb6e16fcf197f2c3df5af2cf41b20974ec8a2'))
  }
}
