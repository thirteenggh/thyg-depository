package org.sonatype.nexus.content.matcher;

import org.sonatype.nexus.repository.content.Asset;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class AssetMatcher
{
  public static FeatureMatcher<Asset, String> path(Matcher<String> matcher) {
    return new FeatureMatcher<Asset, String>(matcher, "path", "path")
    {
      @Override
      protected String featureValueOf(Asset actual) {
        return actual.path();
      }
    };
  }
}
