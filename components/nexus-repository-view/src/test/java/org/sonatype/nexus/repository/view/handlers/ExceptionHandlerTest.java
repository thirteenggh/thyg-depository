package org.sonatype.nexus.repository.view.handlers;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.IllegalOperationException;
import org.sonatype.nexus.repository.InvalidContentException;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Request;

import com.orientechnologies.common.concur.lock.OModificationOperationProhibitedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.orient.testsupport.OrientExceptionMocker.mockOrientException;

public class ExceptionHandlerTest
    extends TestSupport
{
  ExceptionHandler underTest;

  Exception frozenException;

  @Mock
  Context context;

  @Mock
  Request request;

  @Before
  public void setUp() throws Exception {
    when(context.getRequest()).thenReturn(request);
    when(request.getAction()).thenReturn("GET");
    when(request.getPath()).thenReturn("/test");

    underTest = new ExceptionHandler();

    frozenException = mockOrientException(OModificationOperationProhibitedException.class);
  }

  @Test
  public void handleIllegalOperation() throws Exception {
    when(context.proceed()).thenThrow(new IllegalOperationException("That operation was illegal."));
    assertThat(underTest.handle(context).getStatus().getCode(), is(HttpStatus.BAD_REQUEST));
  }

  @Test
  public void handleInvalidContent() throws Exception {
    when(context.proceed()).thenThrow(new InvalidContentException("That content was invalid"));
    assertThat(underTest.handle(context).getStatus().getCode(), is(HttpStatus.NOT_FOUND));
  }

  @Test
  public void handleInvalidContentPut() throws Exception {
    when(request.getAction()).thenReturn("PUT");
    when(context.proceed()).thenThrow(new InvalidContentException("That content was invalid"));
    assertThat(underTest.handle(context).getStatus().getCode(), is(HttpStatus.BAD_REQUEST));
  }

  @Test
  public void handleOModificationOperationProhibited() throws Exception {
    when(context.proceed()).thenThrow(frozenException);
    assertThat(underTest.handle(context).getStatus().getCode(), is(HttpStatus.SERVICE_UNAVAILABLE));
  }
}
