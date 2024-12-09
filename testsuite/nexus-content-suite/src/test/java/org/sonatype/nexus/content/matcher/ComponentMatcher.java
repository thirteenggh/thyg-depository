package org.sonatype.nexus.content.matcher;

import org.sonatype.nexus.repository.content.Component;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

public class ComponentMatcher
{
  public static FeatureMatcher<Component, String> name(Matcher<String> matcher) {
    return new FeatureMatcher<Component, String>(matcher, "name", "name")
    {
      @Override
      protected String featureValueOf(Component actual) {
        return actual.name();
      }
    };
  }
}
