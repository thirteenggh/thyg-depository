package org.sonatype.nexus.internal.security

import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.security.ClientInfo
import org.sonatype.nexus.security.ClientInfoProvider
import org.sonatype.nexus.security.authc.AuthenticationEvent
import org.sonatype.nexus.security.authc.AuthenticationFailureReason
import org.sonatype.nexus.security.authc.NexusAuthenticationEvent

import spock.lang.Specification

class AuthenticationEventSubscriberTest
    extends Specification
{

  AuthenticationEventSubscriber authenticationEventSubscriber

  EventManager eventManager

  ClientInfoProvider clientInfoProvider

  def setup() {
    eventManager = Mock(EventManager.class)
    clientInfoProvider = Mock(ClientInfoProvider.class)

    authenticationEventSubscriber = new AuthenticationEventSubscriber(
        { -> eventManager },
        { -> clientInfoProvider })
  }

  def 'test success event with client info'() {
    given: 'a successful authentication event'
      def clientInfo = ClientInfo
          .builder()
          .path('/path/to/artifact')
          .remoteIP('remote ip')
          .userAgent('user agent string')
          .userId('user id')
          .build()

      clientInfoProvider.getCurrentThreadClientInfo() >> clientInfo

      AuthenticationEvent authenticationEvent = new AuthenticationEvent(
          'user id',
          true
      )

      NexusAuthenticationEvent nexusAuthenticationEvent

    when: 'the event subscriber receives the event'
      authenticationEventSubscriber.on(authenticationEvent)

    then: 'it builds an appropriate nexus authentication event and fires it'
      1 * eventManager.post(_) >> { nexusAuthenticationEvent = it[0] }
      nexusAuthenticationEvent.getAuthenticationFailureReasons().isEmpty()
      nexusAuthenticationEvent.isSuccessful()
      nexusAuthenticationEvent.getClientInfo() == clientInfo
  }

  def 'test success event without client info'() {
    given: 'a successful authentication event - from a client who has no info '
      clientInfoProvider.getCurrentThreadClientInfo() >> null

      AuthenticationEvent authenticationEvent = new AuthenticationEvent(
          'user id',
          true
      )

      NexusAuthenticationEvent nexusAuthenticationEvent

    when: 'the event subscriber receives the event'
      authenticationEventSubscriber.on(authenticationEvent)

    then: 'it builds an appropriate nexus authentication event and fires it'
      1 * eventManager.post(_) >> { nexusAuthenticationEvent = it[0] }
      nexusAuthenticationEvent.getAuthenticationFailureReasons().isEmpty()
      nexusAuthenticationEvent.isSuccessful()
      nexusAuthenticationEvent.getClientInfo().getUserid() == authenticationEvent.getUserId()
  }

  def 'test failed event'() {
    given: 'a failed authentication event'
      clientInfoProvider.getCurrentThreadClientInfo() >> null

      AuthenticationEvent authenticationEvent = new AuthenticationEvent(
          'user id',
          false,
          [AuthenticationFailureReason.INCORRECT_CREDENTIALS] as Set<AuthenticationFailureReason>
      )

      NexusAuthenticationEvent nexusAuthenticationEvent

    when: 'the event subscriber receives the event'
      authenticationEventSubscriber.on(authenticationEvent)

    then: 'it builds an appropriate nexus authentication event and fires it'
      1 * eventManager.post(_) >> { nexusAuthenticationEvent = it[0] }
      nexusAuthenticationEvent.getAuthenticationFailureReasons().size() == 1
      !nexusAuthenticationEvent.isSuccessful()
      nexusAuthenticationEvent.getAuthenticationFailureReasons().
          contains(AuthenticationFailureReason.INCORRECT_CREDENTIALS)
  }
}
