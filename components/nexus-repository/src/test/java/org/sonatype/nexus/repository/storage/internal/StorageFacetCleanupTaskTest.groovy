package org.sonatype.nexus.repository.storage.internal

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.storage.StorageFacetManager

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.mockito.Mockito.times
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class StorageFacetCleanupTaskTest
    extends TestSupport
{
  @Mock
  StorageFacetManager storageFacetManager

  private StorageFacetCleanupTask underTest

  @Before
  public void setUp() {
    underTest = new StorageFacetCleanupTask(storageFacetManager)
  }

  @Test
  void 'task attempts deletions until no more entries were successfully deleted'() {
    when(storageFacetManager.performDeletions()).thenReturn(2L, 1L, 0L)
    underTest.execute()
    verify(storageFacetManager, times(3)).performDeletions()
  }
}
