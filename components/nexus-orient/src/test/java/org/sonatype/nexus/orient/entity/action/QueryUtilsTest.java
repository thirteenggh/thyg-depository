package org.sonatype.nexus.orient.entity.action;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.sonatype.nexus.orient.entity.action.QueryUtils.buildIn;

public class QueryUtilsTest
    extends TestSupport
{
  @Test(expected = NullPointerException.class)
  public void testBuildPredicate_NullArray() {
    QueryUtils.buildPredicate((String[]) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuildPredicate_EmptyArray() {
    QueryUtils.buildPredicate(new String[0]);
  }

  @Test
  public void testBuildPredicate_SingleProperty() {
    assertThat(QueryUtils.buildPredicate("foo"), is("foo = ?"));
  }

  @Test
  public void testBuildPredicate_MultipleProperties() {
    assertThat(QueryUtils.buildPredicate("foo", "bar", "baz"), is("foo = ? AND bar = ? AND baz = ?"));
  }

  @Test
  public void testInPredicate() {
    assertThat(buildIn(newHashSet("foo", "bar")), anyOf(equalTo("['foo','bar']"), equalTo("['bar','foo']")));
  }
}
