package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.TaskScheduler;

import org.junit.rules.ExternalResource;

public class ScheduledTaskRule
    extends ExternalResource
{
  private Provider<TaskScheduler> taskSchedulerProvider;

  private List<TaskInfo> tasks = new ArrayList<>();

  public ScheduledTaskRule(final Provider<TaskScheduler> taskSchedulerProvider) {
    this.taskSchedulerProvider = taskSchedulerProvider;
  }

  public TaskInfo create(final String name, final String typeId, final Map<String, String> attributes) {
    TaskScheduler taskScheduler = taskSchedulerProvider.get();

    TaskConfiguration taskConfiguration = taskScheduler.createTaskConfigurationInstance(typeId);
    attributes.forEach(taskConfiguration::setString);
    taskConfiguration.setName(name);
    taskConfiguration.setEnabled(true);

    TaskInfo taskInfo = taskScheduler.scheduleTask(taskConfiguration, taskScheduler.getScheduleFactory().manual());
    tasks.add(taskInfo);
    return taskInfo;
  }

  public void removeAll() {
    taskSchedulerProvider.get().listsTasks().forEach(TaskInfo::remove);
    tasks.clear();
  }

  @Override
  protected void after() {
    tasks.forEach(TaskInfo::remove);
    tasks.clear();
  }
}
