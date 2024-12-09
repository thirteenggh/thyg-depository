package org.sonatype.nexus.scheduling.internal.resources.doc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.sonatype.nexus.scheduling.api.TaskXO;
import org.sonatype.nexus.rest.Page;

/**
 * Swagger documentation for {@link TasksResource}
 *
 * @since 3.6
 */
@Api(value = "Tasks")
public interface TasksResourceDoc
{
  @ApiOperation("List tasks")
  Page<TaskXO> getTasks(@ApiParam(value = "Type of the tasks to get") final String type);

  @ApiOperation("Get a single task by id")
  @ApiResponses(value = {
      @ApiResponse(code = 404, message = "Task not found")
  })
  TaskXO getTaskById(@ApiParam(value = "Id of the task to get") final String id);

  @ApiOperation("Run task")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Task was run"),
      @ApiResponse(code = 404, message = "Task not found"),
      @ApiResponse(code = 405, message = "Task is disabled")
  })
  void run(@ApiParam(value = "Id of the task to run") final String id);

  @ApiOperation("Stop task")
  @ApiResponses(value = {
      @ApiResponse(code = 204, message = "Task was stopped"),
      @ApiResponse(code = 409, message = "Unable to stop task"),
      @ApiResponse(code = 404, message = "Task not found")
  })
  void stop(@ApiParam(value = "Id of the task to stop") final String id);
}
