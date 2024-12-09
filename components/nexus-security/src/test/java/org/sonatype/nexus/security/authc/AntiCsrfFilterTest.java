package org.sonatype.nexus.security.authc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AntiCsrfFilterTest
  extends TestSupport
{
  private AntiCsrfFilter underTest;

  @Mock
  private AntiCsrfHelper antiCrsfHelper;

  @Mock
  private PrintWriter printWriter;

  @Mock
  HttpServletRequest httpServletRequest;

  @Mock
  HttpServletResponse httpServletResponse;

  @Before
  public void setup() throws IOException {
    underTest = new AntiCsrfFilter(antiCrsfHelper) {
      @Override
      protected Subject getSubject(final ServletRequest request, final ServletResponse response) {
        return null;
      }
    };
    when(httpServletResponse.getWriter()).thenReturn(printWriter);
  }

  @Test
  public void testOnAccessDenied() throws IOException {
    assertFalse(underTest.onAccessDenied(httpServletRequest, httpServletResponse));
    verify(httpServletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    verify(httpServletResponse).setContentType("text/plain");
    verify(printWriter).print(AntiCsrfHelper.ERROR_MESSAGE_TOKEN_MISMATCH);
  }

  @Test
  public void testIsEnabled() {
    when(antiCrsfHelper.isEnabled()).thenReturn(true);
    assertTrue(underTest.isEnabled());

    when(antiCrsfHelper.isEnabled()).thenReturn(false);
    assertFalse(underTest.isEnabled());
  }

  @Test
  public void testIsAccessAllowed() {
    when(antiCrsfHelper.isAccessAllowed(httpServletRequest)).thenReturn(true);
    assertTrue(underTest.isAccessAllowed(httpServletRequest, httpServletResponse, null));

    when(antiCrsfHelper.isAccessAllowed(httpServletRequest)).thenReturn(false);
    assertFalse(underTest.isAccessAllowed(httpServletRequest, httpServletResponse, null));
  }
}
