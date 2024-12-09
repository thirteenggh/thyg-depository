package org.sonatype.nexus.scheduling.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.scheduling.TaskScheduler;
import org.sonatype.nexus.scheduling.schedule.ScheduleFactory;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CronExpressionValidatorTest
{
  private CronExpressionValidator validator;

  @Mock
  private TaskScheduler taskScheduler;

  @Mock
  private ScheduleFactory scheduleFactory;

  @Mock
  private ConstraintValidatorContext context;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    when(taskScheduler.getScheduleFactory()).thenReturn(scheduleFactory);

    validator = new CronExpressionValidator(taskScheduler);
  }

  @Test
  public void isValidStripsJavaElFromExceptionMessage() {
    String value = "value ${java el} {el}";
    when(scheduleFactory.cron(any(Date.class), eq(value))).thenThrow(new IllegalArgumentException(value));
    when(context.buildConstraintViolationWithTemplate(any())).thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));

    boolean isValid = validator.isValid(value, context);

    assertThat(isValid, is(false));
    verify(context).buildConstraintViolationWithTemplate("value {java el} {el}");
  }
}
