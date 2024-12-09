package org.sonatype.nexus.scheduling.internal

import java.util.concurrent.Future

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.stateguard.StateGuardModule
import org.sonatype.nexus.scheduling.CurrentState
import org.sonatype.nexus.scheduling.TaskInfo
import org.sonatype.nexus.scheduling.spi.SchedulerSPI

import com.google.inject.AbstractModule
import com.google.inject.Guice
import org.junit.Before
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mock

import static java.util.Arrays.asList
import static org.mockito.Mockito.doReturn
import static org.mockito.Mockito.inOrder
import static org.mockito.Mockito.times
import static org.mockito.Mockito.when
import static org.sonatype.nexus.scheduling.TaskState.RUNNING
import static org.sonatype.nexus.scheduling.TaskState.WAITING

class TaskActivationTest
extends TestSupport {
  @Mock
  SchedulerSPI schedulerSpi

  @Mock
  TaskInfo runningTask

  @Mock
  TaskInfo waitingTask

  @Mock
  CurrentState runningTaskCurrentState

  @Mock
  CurrentState waitingTaskCurrentState

  @Mock
  Future runningTaskFuture

  private TaskActivation underTest

  @Before
  void setUp() {
    when(runningTask.getCurrentState()).thenReturn(runningTaskCurrentState)
    when(runningTaskCurrentState.getState()).thenReturn(RUNNING)
    doReturn(runningTaskFuture).when(runningTaskCurrentState).getFuture()

    when(waitingTask.getCurrentState()).thenReturn(waitingTaskCurrentState)
    when(waitingTaskCurrentState.getState()).thenReturn(WAITING)
    doReturn(null).when(waitingTaskCurrentState).getFuture()

    when(schedulerSpi.listsTasks()).thenReturn(asList(runningTask, waitingTask))
    underTest = Guice.createInjector(
        new AbstractModule() {
          protected void configure() {
            bind(SchedulerSPI.class).toInstance(schedulerSpi)
          }
        }, new StateGuardModule()).getInstance(TaskActivation.class)
    underTest.start()
  }

  @Test
  void 'pause scheduler and cancel tasks on freeze'() {
    InOrder inOrder = inOrder(schedulerSpi, runningTaskFuture)

    underTest.freeze()
    inOrder.verify(schedulerSpi).pause()
    inOrder.verify(runningTaskFuture).cancel(false)

    inOrder.verifyNoMoreInteractions()
  }

  @Test
  void 'restart scheduler when database on unfreeze'() {
    InOrder inOrder = inOrder(schedulerSpi, runningTaskFuture)

    underTest.stop()
    inOrder.verify(schedulerSpi).pause()
    underTest.freeze()
    underTest.start()
    inOrder.verify(schedulerSpi, times(0)).resume()
    underTest.unfreeze()
    inOrder.verify(schedulerSpi).resume()

    inOrder.verifyNoMoreInteractions()
  }

  @Test
  void 'scheduler not resumed on startup if frozen'() {
    InOrder inOrder = inOrder(schedulerSpi, runningTaskFuture)

    underTest.stop()
    inOrder.verify(schedulerSpi).pause()
    underTest.freeze()
    underTest.start()

    inOrder.verifyNoMoreInteractions()
  }
}
