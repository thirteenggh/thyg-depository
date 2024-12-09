package org.sonatype.nexus.orient;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OIndexNameBuilderTest
    extends TestSupport
{
  OIndexNameBuilder underTest = new OIndexNameBuilder();

  @Test
  public void testBuild() {
    String name = underTest.
        type("myclass").
        property("property1").
        property("property2").
        build();

    assertThat(name, is("myclass_property1_property2_idx"));
  }

  @Test(expected = IllegalStateException.class)
  public void testBuildWithoutProperties() {
    String name = underTest.
        type("myclass").
        build();
  }

  @Test
  public void testBuildCaseInsensitive() {
    String name = underTest.
        type("myclass").
        property("property1").
        property("property2").
        caseInsensitive().
        build();

    assertThat(name, is("myclass_property1_property2_ci_idx"));
  }
}
