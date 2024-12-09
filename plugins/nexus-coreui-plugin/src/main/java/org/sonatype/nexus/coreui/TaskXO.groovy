package org.sonatype.nexus.coreui

import javax.validation.constraints.Future
import javax.validation.constraints.NotNull

import org.sonatype.nexus.scheduling.TaskNotificationCondition
import org.sonatype.nexus.scheduling.constraints.CronExpression
import org.sonatype.nexus.validation.group.Create
import org.sonatype.nexus.validation.group.Update

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotBlank

/**
 * Task exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class TaskXO
{
  @NotBlank(groups = [Update, Schedule])
  String id

  @NotNull
  Boolean enabled

  @NotBlank(groups = [Create, Update])
  String name

  @NotBlank(groups = Create)
  String typeId

  String typeName
  String status
  String statusDescription

  Date nextRun
  Date lastRun
  String lastRunResult
  Boolean runnable
  Boolean stoppable
  String timeZoneOffset;

  String alertEmail
  TaskNotificationCondition notificationCondition

  Map<String, String> properties

  @NotBlank(groups = Create)
  String schedule

  @NotNull(groups = OnceToMonthlySchedule)
  @Future(groups = OnceSchedule)
  Date startDate
  Integer[] recurringDays

  @NotBlank(groups = AdvancedSchedule)
  @CronExpression(groups = AdvancedSchedule)
  String cronExpression

  public interface Schedule
  {
    // empty
  }

  public interface AdvancedSchedule
  {
    // empty
  }

  public interface OnceSchedule
  {
    // empty
  }

  public interface OnceToMonthlySchedule
  {
    // empty
  }
}
