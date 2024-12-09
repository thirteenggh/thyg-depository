package org.sonatype.nexus.cleanup.storage.config;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.cleanup.storage.config.RegexCriteriaValidator;
import org.sonatype.nexus.cleanup.storage.config.RegexCriteriaValidator.InvalidExpressionException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegexCriteriaValidatorTest
    extends TestSupport
{
  private static final String EXPRESSION = "org/sonatype";

  @Test(expected = InvalidExpressionException.class)
  public void whenInvalidExpressionExceptionThrown() {
    RegexCriteriaValidator.validate("hello(world");
  }

  @Test
  public void whenValidExpressionQueryIsBuilt() {
    assertThat(RegexCriteriaValidator.validate(EXPRESSION), is(EXPRESSION));
  }
}
