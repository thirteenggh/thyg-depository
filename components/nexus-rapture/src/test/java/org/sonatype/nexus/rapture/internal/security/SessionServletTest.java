package org.sonatype.nexus.rapture.internal.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.google.common.net.HttpHeaders.X_FRAME_OPTIONS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionServletTest
    extends TestSupport
{
  @Mock
  private HttpServletRequest httpServletRequest;

  @Mock
  private HttpServletResponse httpServletResponse;

  @Mock
  private Subject subject;

  private SessionServlet underTest;

  @Before
  public void setup() {
    underTest = new SessionServlet();
    when(subject.isAuthenticated()).thenReturn(true);
    when(subject.getSession(false)).thenReturn(mock(Session.class));
    ThreadContext.bind(subject);
  }

  @After
  public void cleanup() {
    ThreadContext.unbindSubject();
  }

  @Test
  public void testDoPost() throws Exception {
    underTest.doPost(httpServletRequest, httpServletResponse);

    verify(httpServletResponse).setHeader(X_FRAME_OPTIONS, "DENY");
  }

  @Test
  public void testDoDelete() throws Exception {
    when(subject.isAuthenticated()).thenReturn(false);
    when(subject.isRemembered()).thenReturn(false);
    when(subject.getSession(false)).thenReturn(null);

    underTest.doDelete(httpServletRequest, httpServletResponse);

    verify(httpServletResponse).setHeader(X_FRAME_OPTIONS, "DENY");
  }
}
