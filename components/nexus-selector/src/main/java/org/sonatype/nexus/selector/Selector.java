package org.sonatype.nexus.selector;

/**
 * An interface for evaluating selection based on variable input.
 *
 * @since 3.0
 */
public interface Selector
{
  /**
   * Returns {@code true} if this selector matches against the given variables, otherwise {@code false}.
   *
   * @param variableSource the source of variable values
   */
  boolean evaluate(VariableSource variableSource);

  /**
   * Returns SQL representing this selector for use as a 'where' clause against some queryable values.
   *
   * @param sqlBuilder the builder of 'where' clauses for content selectors
   *
   * @throws UnsupportedOperationException if this selector cannot be represented as SQL
   *
   * @since 3.16
   */
  void toSql(SelectorSqlBuilder sqlBuilder);
}
