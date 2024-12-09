package org.sonatype.nexus.repository.search.query;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class DefaultSearchContributionTest
{
  private DefaultSearchContribution defaultSearchContribution = new DefaultSearchContribution();

  @Test
  public void defaultSearchContributionEscapesStartingSlash() {
    BoolQueryBuilder query = QueryBuilders.boolQuery();
    String field = "name";
    String value = "/foo";

    defaultSearchContribution.contribute(query, field, value);

    assertThat(query.toString(), Matchers.containsString("\"query\" : \"\\\\/foo\""));
  }

  @Test
  public void defaultSearchContributionEscapesContainedSlashes() {
    BoolQueryBuilder query = QueryBuilders.boolQuery();
    String field = "name";
    String value = "a/b/";

    defaultSearchContribution.contribute(query, field, value);

    assertThat(query.toString(), Matchers.containsString("\"query\" : \"a\\\\/b\\\\/\""));
  }

}
