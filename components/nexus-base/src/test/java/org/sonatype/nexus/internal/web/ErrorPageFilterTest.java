package org.sonatype.nexus.internal.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.servlet.XFrameOptions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.google.common.net.HttpHeaders.X_FRAME_OPTIONS;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class ErrorPageFilterTest
  extends TestSupport
{
  private ErrorPageFilter underTest;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @Before
  public void setup() {
    XFrameOptions xFrameOptions = new XFrameOptions(true);
    underTest = new ErrorPageFilter(xFrameOptions);
  }

  @Test
  public void testDoFilter_properXframeOptions() throws Exception {
    doThrow(new IOException("test")).when(filterChain).doFilter(any(), any());

    underTest.doFilter(request, response, filterChain);

    verify(response).setHeader(X_FRAME_OPTIONS, "DENY");
  }
}
