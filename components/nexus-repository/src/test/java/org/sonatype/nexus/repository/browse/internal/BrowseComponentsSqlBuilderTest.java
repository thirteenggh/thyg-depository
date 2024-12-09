package org.sonatype.nexus.repository.browse.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.storage.ComponentEntityAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BrowseComponentsSqlBuilderTest
    extends TestSupport
{
  @Mock
  ComponentEntityAdapter componentEntityAdapter;

  BrowseComponentsSqlBuilder underTest;

  @Before
  public void setUp() {
    underTest = new BrowseComponentsSqlBuilder(componentEntityAdapter);
    when(componentEntityAdapter.getTypeName()).thenReturn("component");
  }

  @Test
  public void buildQueryToReturnNothingWhenBucketsIsEmpty() throws Exception {
    assertThat(underTest.buildBrowseSql(emptyList(), mock(QueryOptions.class)), is(""));
  }
}
