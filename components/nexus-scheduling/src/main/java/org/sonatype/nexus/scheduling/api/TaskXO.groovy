package org.sonatype.nexus.scheduling.api

import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import org.sonatype.nexus.scheduling.TaskInfo

/**
 * Task transfer object for REST APIs.
 * 
 * @since 3.6
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
@EqualsAndHashCode(includes = ['id'])
class TaskXO
{
  String id

  String name

  String type

  String message

  String currentState

  String lastRunResult

  Date nextRun

  Date lastRun

  static TaskXO fromTaskInfo(final TaskInfo taskInfo) {
    TaskXO taskXO = new TaskXO()

    taskXO.id = taskInfo.id
    taskXO.name = taskInfo.name
    taskXO.type = taskInfo.typeId
    taskXO.message = taskInfo.message
    taskXO.currentState = taskInfo.currentState.state.toString()
    taskXO.nextRun = taskInfo.currentState.nextRun
    if (taskInfo.lastRunState != null) {
      taskXO.lastRunResult = taskInfo.lastRunState.endState?.toString()
      taskXO.lastRun = taskInfo.lastRunState.runStarted
    }
    return taskXO
  }
}
