package org.sonatype.nexus.repository.browse.internal;

import java.util.HashMap;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.security.RepositorySelector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PreviewAssetsSqlBuilderTest
    extends TestSupport
{
  static final String CONTENT_EXPRESSION = "contentExpression(@this, :jexlExpression, :repositorySelector, " +
      ":repoToContainedGroupMap) == true";

  static final String FILTER_EXPRESSION = "name LIKE :nameFilter";

  @Mock
  RepositorySelector repositorySelector;

  @Mock
  QueryOptions queryOptions;

  @Mock
  Repository repository;

  PreviewAssetsSqlBuilder underTest;

  @Before
  public void setup() throws Exception {
    underTest = new PreviewAssetsSqlBuilder(repositorySelector,
        "",
        queryOptions,
        new HashMap<>());
  }

  @Test
  public void whereWithContentExpression() throws Exception {
    String whereClause = underTest.buildWhereClause();
    assertThat(whereClause, is(equalTo(CONTENT_EXPRESSION)));
  }

  @Test
  public void whereWithFilter() throws Exception {
    when(queryOptions.getFilter()).thenReturn("filter");
    String whereClause = underTest.buildWhereClause();
    assertThat(whereClause, is(equalTo(CONTENT_EXPRESSION + " AND " + FILTER_EXPRESSION)));
  }
}
