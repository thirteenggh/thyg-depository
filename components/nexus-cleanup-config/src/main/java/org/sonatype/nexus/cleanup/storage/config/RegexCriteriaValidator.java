package org.sonatype.nexus.cleanup.storage.config;

import org.apache.lucene.util.automaton.RegExp;

import static java.lang.String.format;

/**
 * @since 3.19
 */
public class RegexCriteriaValidator
{
  private RegexCriteriaValidator() {
  }

  /**
   * Ensures that a regular expression entered is a valid pattern.
   *
   * @param expression
   * @throws InvalidExpressionException when the expression is deemed invalid
   */
  public static String validate(final String expression) {
    try {
      new RegExp(expression);
    }
    catch (IllegalArgumentException e) {  // NOSONAR
      throw new InvalidExpressionException(
          format("Invalid regular expression pattern: %s", e.getMessage()));
    }
    return expression;
  }

  public static class InvalidExpressionException
      extends RuntimeException
  {
    InvalidExpressionException(final String message) {
      super(message);
    }
  }
}
