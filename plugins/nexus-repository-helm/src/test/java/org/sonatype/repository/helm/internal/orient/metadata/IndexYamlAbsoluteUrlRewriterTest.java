package org.sonatype.repository.helm.internal.orient.metadata;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.repository.helm.internal.metadata.IndexYamlAbsoluteUrlRewriter;

import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class IndexYamlAbsoluteUrlRewriterTest
    extends TestSupport
{
  private static final String INDEX_YAML = "index.yaml";

  private static final String INDEX_YAML_NO_ABSOLUTE_URLS = "indexWithRelativeUrls.yaml";

  private static final String INDEX_YAML_WITH_CUSTOM_URL = "index.yaml";

  private static final String INDEX_YAML_PREFIXES = "indexWithPrefixes.yaml";

  private static final String INDEX_YAML_URL_NODE = "url";

  private static final String HTTP = "http://";

  private static final String HTTPS = "https://";

  private IndexYamlAbsoluteUrlRewriter underTest;

  @Mock
  private Content tempContent;

  @Mock
  Repository repository;

  @Mock
  StorageFacet storageFacet;

  @Before
  public void setUp() throws Exception {
    setupRepositoryMock();
    this.underTest = new IndexYamlAbsoluteUrlRewriter();
  }

  @Test
  public void checkCustomUrls() throws Exception {
    setupIndexMock(INDEX_YAML_WITH_CUSTOM_URL);
    Content newTempBlob = underTest.removeUrlsFromIndexYaml(tempContent);
    checkThatAbsoluteUrlRemoved(newTempBlob.openInputStream());
  }

  @Test
  public void removeUrlsFromIndexYaml() throws Exception {
    setupIndexMock(INDEX_YAML);
    Content newTempBlob = underTest.removeUrlsFromIndexYaml(tempContent);
    checkThatAbsoluteUrlRemoved(newTempBlob.openInputStream());
  }

  @Test
  public void doNotModifyUrlsWhenAlreadyRelative() throws Exception {
    setupIndexMock(INDEX_YAML_NO_ABSOLUTE_URLS);
    Content newTempBlob = underTest.removeUrlsFromIndexYaml(tempContent);
    checkThatAbsoluteUrlRemoved(newTempBlob.openInputStream());
  }

  @Test
  public void modifyUrlsWithPrefixes() throws Exception {
    setupIndexMock(INDEX_YAML_PREFIXES);
    Content newTempBlob = underTest.removeUrlsFromIndexYaml(tempContent);
    try (InputStream is = newTempBlob.openInputStream();
         InputStreamReader inputStreamReader = new InputStreamReader(is, defaultCharset())) {
      String actual = deleteWhitespace(CharStreams.toString(inputStreamReader));
      String expected = deleteWhitespace(Resources.toString(
          Resources.getResource(getClass(), "indexWithPrefixesExpected.yaml"), defaultCharset()));
      assertEquals(expected, actual);
      checkThatAbsoluteUrlRemoved(IOUtils.toInputStream(String.join("\n", actual), defaultCharset()));
    }
  }

  private void checkThatAbsoluteUrlRemoved(final InputStream is) throws Exception {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
      boolean checkNext = false;
      String line;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (checkNext) {
          assertThat(line, either(not(containsString(HTTP))).or(not(containsString(HTTPS))));
          assertThat(line, not(startsWith("- /")));
          checkNext = false;
        }
        if (line.contains(INDEX_YAML_URL_NODE)) {
          checkNext = true;
        }
      }
    }
  }

  /**
   * snakeyaml in versions other than 1.20 seems to add a ! to every line, this ensures we don't regress
   */
  @Test
  public void ensureNoExclamationMarksInYaml() throws Exception {
    setupIndexMock(INDEX_YAML);
    Content newTempBlob = underTest.removeUrlsFromIndexYaml(tempContent);
    BufferedReader reader = new BufferedReader(new InputStreamReader(newTempBlob.openInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      line = line.trim();
      assertThat(line, is(not("!")));
    }
    reader.close();
  }

  private void setupIndexMock(final String indexYamlName) throws Exception {
    when(tempContent.openInputStream()).thenReturn(getClass().getResourceAsStream(indexYamlName));
    when(tempContent.getAttributes()).thenReturn(new AttributesMap());
    when(tempContent.getContentType()).thenReturn("text/x-yaml");
  }

  private void setupRepositoryMock() {
    when(repository.facet(StorageFacet.class)).thenReturn(storageFacet);
    when(storageFacet.createTempBlob(any(InputStream.class), any(Iterable.class))).thenAnswer(args -> {
      InputStream inputStream = (InputStream) args.getArguments()[0];
      byte[] bytes = IOUtils.toByteArray(inputStream);
      when(tempContent.openInputStream()).thenReturn(new ByteArrayInputStream(bytes));
      when(tempContent.getAttributes()).thenReturn(new AttributesMap());
      when(tempContent.getContentType()).thenReturn("text/x-yaml");
      return tempContent;
    });
  }
}
