package org.sonatype.nexus.scheduling.constraints;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.scheduling.TaskScheduler;
import org.sonatype.nexus.scheduling.schedule.ScheduleFactory;
import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link CronExpression} validator.
 *
 * @see ScheduleFactory#cron(Date, String)
 * @since 3.0
 */
@Named
public class CronExpressionValidator
    extends ConstraintValidatorSupport<CronExpression, String>
{
  private final ScheduleFactory scheduleFactory;

  @Inject
  public CronExpressionValidator(final TaskScheduler taskScheduler) {
    checkNotNull(taskScheduler);
    this.scheduleFactory = taskScheduler.getScheduleFactory();
  }

  @Override
  public void initialize(final CronExpression annotation) {
    // nop
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    try {
      scheduleFactory.cron(new Date(), value);
    }
    catch (IllegalArgumentException e) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(getEscapeHelper().stripJavaEl(e.getMessage()))
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
