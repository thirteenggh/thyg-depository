package org.sonatype.nexus.audit.internal

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.security.ClientInfo
import org.sonatype.nexus.security.ClientInfoProvider

import org.apache.shiro.subject.Subject
import org.apache.shiro.util.ThreadContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests for {@link InitiatorProviderImpl}.
 */
class InitiatorProviderImplTest
    extends TestSupport
{
  @Mock
  ClientInfoProvider clientInfoProvider

  InitiatorProviderImpl underTest

  private static void reset() {
    ThreadContext.unbindSubject()
    ThreadContext.unbindSecurityManager()
  }

  private static Subject subject(final Object principal) {
    Subject subject = mock(Subject.class)
    when(subject.getPrincipal()).thenReturn(principal)
    return subject
  }

  @Before
  void setUp() {
    reset()

    underTest = new InitiatorProviderImpl(clientInfoProvider)
  }

  @After
  void tearDown() {
    reset()
  }

  @Test
  void 'prefer clientinfo when available'() {
    ClientInfo clientInfo = ClientInfo.builder().userId('foo').remoteIP('1.2.3.4').userAgent('bar').build()
    when(clientInfoProvider.getCurrentThreadClientInfo()).thenReturn(clientInfo)

    def result = underTest.get()
    assert result == 'foo/1.2.3.4'
  }

  @Test
  void 'when clientinfo missing uses subject'() {
    ThreadContext.bind(subject('foo'))
    def result = underTest.get()
    assert result == 'foo'
  }
}
