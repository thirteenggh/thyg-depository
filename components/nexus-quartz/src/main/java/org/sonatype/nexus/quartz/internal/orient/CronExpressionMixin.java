package org.sonatype.nexus.quartz.internal.orient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.quartz.CronExpression;

/**
 * Jackson Mixin for {@link CronExpression}.
 *
 * @since 3.0
 */
public abstract class CronExpressionMixin
{
  /**
   * Inform Jackson to use {@link CronExpression#CronExpression(String)} for construction.
   */
  @JsonCreator
  public CronExpressionMixin(@JsonProperty("cronExpression") final String expression) {
    // empty
  }
}
