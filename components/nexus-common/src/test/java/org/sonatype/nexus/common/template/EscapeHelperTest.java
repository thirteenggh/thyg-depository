package org.sonatype.nexus.common.template;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EscapeHelperTest
{
  EscapeHelper underTest;

  @Before
  public void setup() {
    underTest = new EscapeHelper();
  }

  @Test
  public void testStripJavaEl() {
    String test = "${badstuffinhere}";
    String result = underTest.stripJavaEl(test);
    assertThat(result, is("{badstuffinhere}"));
  }

  @Test
  public void testStripJavaEl_multiple_dollar_signs() {
    String test = "$$$$${badstuffinhere}";
    String result = underTest.stripJavaEl(test);
    assertThat(result, is("{badstuffinhere}"));
  }

  @Test
  public void testStripJavaEl_bugged_interpolator() {
    String test = "$\\A{badstuffinhere}";
    String result = underTest.stripJavaEl(test);
    assertThat(result, is("{badstuffinhere}"));
  }
}
