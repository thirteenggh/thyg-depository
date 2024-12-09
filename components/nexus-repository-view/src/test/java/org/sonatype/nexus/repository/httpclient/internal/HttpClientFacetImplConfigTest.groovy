package org.sonatype.nexus.repository.httpclient.internal

import javax.validation.Validation
import javax.validation.Validator

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.httpclient.HttpClientManager
import org.sonatype.nexus.httpclient.config.UsernameAuthenticationConfiguration
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.httpclient.RemoteConnectionStatus
import org.sonatype.nexus.repository.httpclient.RemoteConnectionStatusEvent

import org.junit.Before
import org.junit.Test

import static org.mockito.Matchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.sonatype.nexus.repository.httpclient.RemoteConnectionStatusType.AVAILABLE
import static org.sonatype.nexus.repository.httpclient.RemoteConnectionStatusType.BLOCKED

/**
 * Tests for {@link HttpClientFacetImpl.Config}.
 */
class HttpClientFacetImplConfigTest
    extends TestSupport
{
  private Validator validator

  EventManager eventManager = mock(EventManager.class)

  @Before
  public void setUp() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().validator
  }

  @Test
  void 'authentication username with null password'() {
    def violations = validator.validate(new HttpClientFacetImpl.Config(
        authentication: new UsernameAuthenticationConfiguration(
            username: 'admin',
            password: null
        )
    ))
    assert violations.size() == 1
    def violation = violations.iterator().next()
    assert violation.propertyPath.toString() == 'authentication.password'
  }

  @Test
  void 'authentication password with null username'() {
    def violations = validator.validate(new HttpClientFacetImpl.Config(
        authentication: new UsernameAuthenticationConfiguration(
            username: null,
            password: 'pass'
        )
    ))
    assert violations.size() == 1
    def violation = violations.iterator().next()
    assert violation.propertyPath.toString() == 'authentication.username'
  }

  @Test
  void 'required fields may not be whitespace only'() {
    def violations = validator.validate(new HttpClientFacetImpl.Config(
        authentication: new UsernameAuthenticationConfiguration(
            username: ' ',
            password: ' '
        )
    ))
    assert violations.size() == 2
    assert violations.collect { it.propertyPath.toString() }.sort() == ['authentication.password', 'authentication.username']
  }

  @Test
  void 'fire event on remote connection status changed'() {
    def underTest = new HttpClientFacetImpl(mock(HttpClientManager.class), [:], [:], [:], [:], [:])
    underTest.attach(mock(Repository.class))
    underTest.installDependencies(eventManager)
    underTest.onStatusChanged(new RemoteConnectionStatus(AVAILABLE), new RemoteConnectionStatus(BLOCKED))
    verify(eventManager).post(any(RemoteConnectionStatusEvent.class))
  }
}
