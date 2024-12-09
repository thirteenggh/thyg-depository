package org.sonatype.nexus.repository.content.security.internal;

import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.VariableSource;

import org.elasticsearch.search.lookup.SourceLookup;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class SimpleVariableResolverAdapterTest
    extends TestSupport
{
  private static final String FORMAT_VARIABLE = "format";

  private static final String PATH_VARIABLE = "path";

  private static final String TEST_PATH_WITHOUT_SLASH = "some/path.txt";

  private static final String TEST_PATH_WITH_SLASH = '/' + TEST_PATH_WITHOUT_SLASH;

  private static final String TEST_FORMAT = "test";

  @Mock
  Request request;

  @Mock
  SourceLookup sourceLookup;

  @Mock
  Map<String, Object> sourceLookupAsset;

  @Mock
  Repository repository;

  @Mock
  FluentAsset asset;

  @Test
  public void testFromRequest() throws Exception {
    when(request.getPath()).thenReturn(TEST_PATH_WITH_SLASH);
    when(repository.getName()).thenReturn("SimpleVariableResolverAdapterTest");
    when(repository.getFormat()).thenReturn(new Format(TEST_FORMAT) { });
    SimpleVariableResolverAdapter simpleVariableResolverAdapter = new SimpleVariableResolverAdapter();
    VariableSource source = simpleVariableResolverAdapter.fromRequest(request, repository);

    assertThat(source.getVariableSet(), containsInAnyOrder(FORMAT_VARIABLE, PATH_VARIABLE));
    assertThat(source.get(FORMAT_VARIABLE).get(), is(TEST_FORMAT));
    assertThat(source.get(PATH_VARIABLE).get(), is(TEST_PATH_WITHOUT_SLASH));
  }

  @Test
  public void testFromAsset() throws Exception {
    when(asset.path()).thenReturn(TEST_PATH_WITH_SLASH);
    when(asset.repository()).thenReturn(repository);
    when(repository.getFormat()).thenReturn(new Format(TEST_FORMAT) { });

    SimpleVariableResolverAdapter simpleVariableResolverAdapter = new SimpleVariableResolverAdapter();
    VariableSource source = simpleVariableResolverAdapter.fromAsset(asset);

    assertThat(source.getVariableSet(), containsInAnyOrder(FORMAT_VARIABLE, PATH_VARIABLE));
    assertThat(source.get(FORMAT_VARIABLE).get(), is(TEST_FORMAT));
    assertThat(source.get(PATH_VARIABLE).get(), is(TEST_PATH_WITHOUT_SLASH));
  }

  @Test
  public void testFromSourceLookup() throws Exception {
    when(sourceLookupAsset.get("name")).thenReturn(TEST_PATH_WITHOUT_SLASH);
    when(sourceLookup.get("format")).thenReturn(TEST_FORMAT);

    SimpleVariableResolverAdapter simpleVariableResolverAdapter = new SimpleVariableResolverAdapter();
    VariableSource source = simpleVariableResolverAdapter.fromSourceLookup(sourceLookup, sourceLookupAsset);

    assertThat(source.getVariableSet(), containsInAnyOrder(FORMAT_VARIABLE, PATH_VARIABLE));
    assertThat(source.get(FORMAT_VARIABLE).get(), is(TEST_FORMAT));
    assertThat(source.get(PATH_VARIABLE).get(), is(TEST_PATH_WITHOUT_SLASH));
  }
}
