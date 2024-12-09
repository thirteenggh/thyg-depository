package org.sonatype.nexus.repository.npm.security;

import javax.servlet.http.HttpServletRequest;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.sonatype.nexus.repository.npm.security.NpmToken;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.UUID.randomUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;

public class NpmTokenTest
    extends TestSupport
{
  private static final String TOKEN = randomUUID().toString();

  @Mock
  private HttpServletRequest request;

  NpmToken underTest;

  @Before
  public void setup() throws Exception {
    underTest = new NpmToken();
  }

  @Test
  public void extractNpmToken() throws Exception {
    when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer NpmToken." + TOKEN);
    String token = underTest.extract(request);
    assertThat(token, is(equalTo(TOKEN)));
  }

  @Test
  public void shouldNotMatchOtherFormat() throws Exception {
    when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer NotNpm." + TOKEN);
    String token = underTest.extract(request);
    assertThat(token, is(nullValue()));
  }

  @Test
  public void extractNpmTokenWhenFormatNotPresent() throws Exception {
    when(request.getHeader(AUTHORIZATION)).thenReturn("Bearer " + TOKEN);
    String token = underTest.extract(request);
    assertThat(token, is(equalTo(TOKEN)));
  }
}
