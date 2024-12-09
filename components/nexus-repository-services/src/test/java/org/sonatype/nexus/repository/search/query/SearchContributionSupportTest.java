package org.sonatype.nexus.repository.search.query;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SearchContributionSupportTest
{
  private SearchContributionSupport searchContributionSupport = new SearchContributionSupport();

  @Test
  public void escapeLeavesRegularCharactersAsIs() {
    String regularCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.";
    assertThat(searchContributionSupport.escape(regularCharacters), is(regularCharacters));
  }

  @Test
  public void escapeLeavesSupportedSpecialCharactersUnescaped() {
    String supportedSpecialCharacters = "?*\"\"";
    assertThat(searchContributionSupport.escape(supportedSpecialCharacters), is(supportedSpecialCharacters));
  }

  @Test
  public void escapeEscapesAllUnsupportedSpecialCharacters() {
    assertThat(
        searchContributionSupport.escape(":[]-+!(){}^~/\\"),
        is("\\:\\[\\]\\-\\+\\!\\(\\)\\{\\}\\^\\~\\/\\\\")
    );
  }

  @Test
  public void escapeEscapesOddNumberOfDoubleQuotes() {
    assertThat(searchContributionSupport.escape("\""), is("\\\""));
    assertThat(searchContributionSupport.escape("\"a\"b\""), is("\\\"a\\\"b\\\""));
  }

  @Test
  public void escapeIgnoresEvenNumberOfDoubleQuotes() {
    assertThat(searchContributionSupport.escape("\"ab\""), is("\"ab\""));
    assertThat(searchContributionSupport.escape("\"ab\" \"ab\""), is("\"ab\" \"ab\""));
    assertThat(searchContributionSupport.escape("\"\"\"\""), is("\"\"\"\""));
  }

  @Test
  public void escapeSupportsCommonSearches() {
    assertThat(searchContributionSupport.escape("library/alpine-dev"), is("library\\/alpine\\-dev"));

    String mavenGroup = "org.sonatype.nexus";
    assertThat(searchContributionSupport.escape(mavenGroup), is(mavenGroup));

    assertThat(
        searchContributionSupport.escape("org.apache.maven maven-plugin-registry"),
        is("org.apache.maven maven\\-plugin\\-registry")
    );
  }
}
