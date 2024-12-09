package org.sonatype.nexus.repository.view.handlers;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * UT for {@link FormatHighAvailabilitySupportHandler}
 *
 * @since 3.17
 */
public class FormatHighAvailabilitySupportHandlerTest
    extends TestSupport
{
  private final String FORMAT_NAME = "dummyFormat";

  @Mock
  private Context context;

  @Mock
  private Repository repository;

  @Mock
  private Format format;

  @Mock
  private HighAvailabilitySupportChecker highAvailabilitySupportChecker;

  private FormatHighAvailabilitySupportHandler handlerUnderTest;

  @Before
  public void setup() throws Exception {
    handlerUnderTest = new FormatHighAvailabilitySupportHandler(highAvailabilitySupportChecker);
    when(context.getRepository()).thenReturn(repository);
    when(context.getRepository().getFormat()).thenReturn(format);
    when(format.getValue()).thenReturn(FORMAT_NAME);
    when(context.proceed()).thenReturn(HttpResponses.ok());
  }

  @Test
  public void responseIsOk_IfNexusIsNotClusteredAndFormatIsNotClustered() throws Exception {
    when(highAvailabilitySupportChecker.isSupported(FORMAT_NAME)).thenReturn(true);
    final Response response = handlerUnderTest.handle(context);
    verify(highAvailabilitySupportChecker).isSupported(FORMAT_NAME);
    assertThat(response.getStatus().getCode(), is(HttpStatus.OK));
  }

  @Test
  public void responseIsBadRequest_IfNexusIsClusteredAndFormatIsNotClustered() throws Exception {
    when(highAvailabilitySupportChecker.isSupported(FORMAT_NAME)).thenReturn(false);
    final Response response = handlerUnderTest.handle(context);
    verify(highAvailabilitySupportChecker).isSupported(FORMAT_NAME);
    assertThat(response.getStatus().getCode(), is(HttpStatus.BAD_REQUEST));
  }
}
