package org.sonatype.repository.helm.internal.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;
import org.sonatype.repository.helm.internal.metadata.IndexYamlAbsoluteUrlRewriter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelmPathUtilsTest
    extends TestSupport
{
  private static final String FILENAME = "mongodb-0.4.9.tgz";

  private HelmPathUtils underTest;

  @Mock
  private TokenMatcher.State state;

  @Before
  public void setUp() throws Exception {
    underTest = new HelmPathUtils(new IndexYamlAbsoluteUrlRewriter());
  }

  @Test
  public void filename() throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("filename", FILENAME);
    when(state.getTokens()).thenReturn(map);
    String result = underTest.filename(state);
    assertThat(result, is(equalTo(FILENAME)));
  }

  @Test
  public void testContentFileUrl() throws IOException {
    Content content = mock(Content.class);
    when(content.openInputStream()).thenReturn(getClass().getResourceAsStream("indexresult.yaml"));
    String url = underTest.contentFileUrl(FILENAME, content);
    assertThat(url, is(equalTo("mongodb-0.5.2.tgz")));
  }
}
