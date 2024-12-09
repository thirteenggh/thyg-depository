package org.sonatype.nexus.scheduling;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.collect.ImmutableMap;
import com.google.inject.util.Providers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;

public class TaskUtilsTest
    extends TestSupport
{
  @Mock
  private TaskScheduler taskScheduler;

  @Mock
  private TaskInfo taskInfo;

  @Mock
  private TaskConfiguration taskConfiguration;

  @Mock
  private CurrentState currentState;

  private TaskUtils underTest;

  @Before
  public void setup() {
    when(taskScheduler.listsTasks()).thenReturn(asList(taskInfo));
    when(taskInfo.getId()).thenReturn("taskId");
    when(taskInfo.getName()).thenReturn("taskName");
    when(taskInfo.getTypeId()).thenReturn("taskTypeId");
    when(taskInfo.getConfiguration()).thenReturn(taskConfiguration);
    when(taskInfo.getCurrentState()).thenReturn(currentState);
    when(currentState.getState()).thenReturn(TaskState.RUNNING);
    when(taskConfiguration.getString("key")).thenReturn("value");
    underTest = new TaskUtils(Providers.of(taskScheduler));
  }

  @Test
  public void testCheckForConflictingTasks_ignoresMatchingWithSameId() {
    underTest
        .checkForConflictingTasks("taskId", "taskName", asList("taskTypeId"), ImmutableMap.of("key", asList("value")));
  }

  @Test
  public void testCheckForConflictingTasks_matchingTaskDiffConfigKey() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("key2", asList("value")));
  }

  @Test
  public void testCheckForConflictingTasks_matchingTaskDiffConfigValue() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("key", asList("value2")));
  }

  @Test
  public void testCheckForConflictingTasks_matchingTaskNotRunning() {
    when(currentState.getState()).thenReturn(TaskState.WAITING);
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("key", asList("value")));
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckForConflictingTasks_conflictingTask() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("key", asList("value")));
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckForConflictingTasks_conflictingTaskMultipleTypeIds() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("preTaskTypeId", "taskTypeId"),
        ImmutableMap.of("key", asList("value")));
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckForConflictingTasks_conflictingTaskMultipleConfigKeys() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("prekey", asList("prevalue"), "key", asList("value")));
  }

  @Test(expected = IllegalStateException.class)
  public void testCheckForConflictingTasks_conflictingTaskMultipleConfigValues() {
    underTest.checkForConflictingTasks("taskId2", "taskName2", asList("taskTypeId"),
        ImmutableMap.of("key", asList("prevalue", "value")));
  }
}
