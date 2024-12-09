package org.sonatype.nexus.repository.browse.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.query.QueryOptions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.sonatype.nexus.repository.browse.internal.SuffixSqlBuilder.buildSuffix;

public class SuffixSqlBuilderTest
    extends TestSupport
{
  QueryOptions queryOptions;

  @Before
  public void setup() throws Exception {
    queryOptions = new QueryOptions("filter", "name", "asc", 99, 10);
  }

  @Test(expected = NullPointerException.class)
  public void failOnNullQueryOptions() throws Exception {
    buildSuffix(null);
  }

  @Test
  public void suffix() throws Exception {
    assertThat(buildSuffix(queryOptions), is(equalTo(" SKIP 99 LIMIT 10")));
  }

  @Test
  public void testId() {
    assertThat(buildSuffix(new QueryOptions("filter", "id", "asc", 99, 10))
        , is(equalTo(" ORDER BY @rid asc SKIP 99 LIMIT 10")));
  }
}
