package org.sonatype.nexus.security.authc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.net.HttpHeaders;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class AntiCsrfHelperTest
  extends TestSupport
{
  private AntiCsrfHelper underTest;

  @Mock
  HttpServletRequest httpServletRequest;

  @Mock
  Subject subject;

  @Before
  public void setup() {
    underTest = new AntiCsrfHelper(true);

    ThreadContext.bind(subject);
  }

  @After
  public void teardown() {
    ThreadContext.unbindSubject();
  }

  /*
   * Test that an exception is not thrown when the CSRF protection is disabled.
   */
  @Test
  public void testRequireValidToken_Disabled() {
    underTest = new AntiCsrfHelper(false);
    underTest.requireValidToken(httpServletRequest, "a-token");
    verifyZeroInteractions(httpServletRequest);
  }

  /*
   * Requests with a session but no token are invalid.
   */
  @Test(expected = UnauthorizedException.class)
  public void testRequireValidToken_Session_NoToken() {
    setupBrowserSubject();
    underTest.requireValidToken(httpServletRequest, null);
  }

  /*
   * Requests without a UserAgent or with an arbitrary UserAgent are likely non-browser clients.
   */
  @Test
  public void testRequireValidToken_NoSessionAndNoReferrer() {
    underTest.requireValidToken(httpServletRequest, null);

    setupClientSubject();
    try {
      underTest.requireValidToken(httpServletRequest, null);
    }
    catch (Exception e) {
      fail("expected requiring a valid token to succeed");
    }
  }

  /*
   * Browser requests with a valid token are allowed
   */
  @Test
  public void testRequireValidToken() {
    setupBrowserSubject();
    when(httpServletRequest.getCookies())
        .thenReturn(new Cookie[] { new Cookie(AntiCsrfHelper.ANTI_CSRF_TOKEN_NAME, "a-value") });
    try {
      underTest.requireValidToken(httpServletRequest, "a-value");
    }
    catch (Exception e) {
      fail("expected requiring a valid token to succeed");
    }
  }

  /*
   * Requests with a browser UserAgent and mismatched tokens are invalid.
   */
  @Test(expected = UnauthorizedException.class)
  public void testRequireValidToken_tokenMismatch() {
    setupBrowserSubject();
    when(httpServletRequest.getCookies())
        .thenReturn(new Cookie[] { new Cookie(AntiCsrfHelper.ANTI_CSRF_TOKEN_NAME, "a-value") });
    underTest.requireValidToken(httpServletRequest, "a-different-value");
  }

  /*
   * Test that the filter passes requests when disabled
   */
  @Test
  public void testIsAccessAllowed_Disabled() {
    underTest = new AntiCsrfHelper(false);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));
    verifyZeroInteractions(httpServletRequest);
  }

  /*
   * Test that the filter allows requests with 'safe' HTTP methods without a token
   */
  @Test
  public void testIsAccessAllowed_SafeMethodsAllowed() {
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.HEAD);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));

    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.GET);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));
  }

  /*
   * Test that the filter blocks requests with 'unsafe' HTTP methods without a token
   */
  @Test
  public void testIsAccessAllowed_UnsafeMethodsBlocked() {
    setupBrowserSubject();
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));

    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.PUT);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));

    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.DELETE);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));
  }

  /*
   * Test the behaviour without a User-Agent or with a non-browser User-Agent.
   * clients.
   */
  @Test
  public void testIsAccessAllowed_NotBrowser() {
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));

    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    setupClientSubject();
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));
  }

  /*
   * PowerShell inexplicably includes a UserAgent pretending to be a browser, this ensures our whitelist allows it.
   */
  @Test
  public void testIsAccessAllowed_PowerShell() {
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));

    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    when(httpServletRequest.getHeader(HttpHeaders.USER_AGENT))
        .thenReturn("Mozilla/5.0 (Windows NT; Windows NT 10.0; en-CA) WindowsPowerShell/5.1.17134.590");
    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));
  }

  @Test
  public void shouldAllowAccessWhenMissingSubject() {
    ThreadContext.unbindSubject();
    underTest = new AntiCsrfHelper(true);

    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));
  }

  /*
   * Test that a request with a valid CSRF token is allowed
   */
  @Test
  public void testIsAccessAllowed_ValidCsrfToken() {
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    setupBrowserSubject();
    when(httpServletRequest.getHeader("NX-ANTI-CSRF-TOKEN")).thenReturn("avalue");
    when(httpServletRequest.getCookies()).thenReturn(new Cookie[] { new Cookie("NX-ANTI-CSRF-TOKEN", "avalue") });

    assertThat(underTest.isAccessAllowed(httpServletRequest), is(true));

    // simple validation, we expect the code to access the cookies once
    verify(httpServletRequest, times(1)).getCookies();
  }

  /*
   * Test that a request missing a CSRF cookie but with a header is rejected
   */
  @Test
  public void testIsAccessAllowed_MissingCsrfCookie() {
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    setupBrowserSubject();
    when(httpServletRequest.getHeader("NX-ANTI-CSRF-TOKEN")).thenReturn("avalue");

    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));

    // simple validation, we expect the code to access the cookies once
    verify(httpServletRequest, times(1)).getCookies();
  }

  /*
   * Test that a request missing a CSRF header but with a cookie is rejected
   */
  @Test
  public void testIsAccessAllowed_MissingCsrfHeader() {
    setupBrowserSubject();
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    // NX-ANTI-CSRF-TOKEN header not set
    when(httpServletRequest.getCookies()).thenReturn(new Cookie[] { new Cookie("NX-ANTI-CSRF-TOKEN", "avalue") });

    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));

    // simple validation, we expect the code to access the cookies once
    verify(httpServletRequest, times(1)).getCookies();
  }

  /*
   * Test that a request with mismatched CSRF tokens is rejected
   */
  @Test
  public void testIsAccessAllowed_MismatchedCsrfToken() {
    setupBrowserSubject();
    when(httpServletRequest.getMethod()).thenReturn(HttpMethod.POST);
    when(httpServletRequest.getHeader(HttpHeaders.REFERER)).thenReturn("referrer");
    when(httpServletRequest.getHeader("NX-ANTI-CSRF-TOKEN")).thenReturn("some-value");
    when(httpServletRequest.getCookies())
        .thenReturn(new Cookie[] { new Cookie("NX-ANTI-CSRF-TOKEN", "some-other-value") });

    assertThat(underTest.isAccessAllowed(httpServletRequest), is(false));

    // simple validation, we expect the code to access the cookies once
    verify(httpServletRequest, times(1)).getCookies();
  }

  private void setupBrowserSubject() {
    Session session = mock(Session.class);
    when(subject.getSession(false)).thenReturn(session);
  }

  private void setupClientSubject() {
    when(subject.getSession(false)).thenReturn(null);
  }
}
