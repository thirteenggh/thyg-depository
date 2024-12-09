package org.sonatype.nexus.repository.storage;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.scheduling.TaskConfiguration;
import org.sonatype.nexus.scheduling.TaskInfo;
import org.sonatype.nexus.scheduling.TaskScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static java.util.Collections.emptyList;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RebuildAssetUploadMetadataTaskManagerTest
    extends TestSupport
{
  private RebuildAssetUploadMetadataTaskManager manager;

  @Mock
  private TaskScheduler taskScheduler;

  @Before
  public void injectMocks() {
    manager = new RebuildAssetUploadMetadataTaskManager(taskScheduler);
  }

  @Test
  public void doStartWithNoOldRebuildTasks() throws Exception {
    when(taskScheduler.listsTasks()).thenReturn(emptyList());

    manager.doStart();

    verify(taskScheduler, never()).scheduleTask(any(), any());
  }

  @Test
  public void doStartRemovedOldTasks() throws Exception {
    TaskInfo existingTask = createMockTaskInfo(RebuildAssetUploadMetadataTaskDescriptor.TYPE_ID);

    when(taskScheduler.listsTasks()).thenReturn(asList(existingTask));

    manager.doStart();

    verify(existingTask).remove();
  }

  private TaskConfiguration createTaskConfiguration() {
    TaskConfiguration configuration = new TaskConfiguration();
    configuration.setMessage("message");
    return configuration;
  }

  private TaskInfo createMockTaskInfo(String typeId) {
    TaskConfiguration taskConfiguration = createTaskConfiguration();
    taskConfiguration.setTypeId(typeId);

    TaskInfo taskInfo = mock(TaskInfo.class);
    when(taskInfo.getConfiguration()).thenReturn(taskConfiguration);
    return taskInfo;
  }
}
