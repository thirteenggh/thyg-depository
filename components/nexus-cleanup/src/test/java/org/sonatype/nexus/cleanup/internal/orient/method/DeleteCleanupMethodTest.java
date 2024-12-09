package org.sonatype.nexus.cleanup.internal.orient.method;

import java.util.function.BooleanSupplier;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.ComponentMaintenance;
import org.sonatype.nexus.repository.storage.DefaultComponentMaintenanceImpl.DeletionProgress;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteCleanupMethodTest
    extends TestSupport
{
  private static final int BATCH_SIZE = 500;

  @Mock
  private Repository repository;

  @Mock
  private EntityId component1, component2;
  
  @Mock
  private BooleanSupplier cancelledCheck;
  
  @Mock
  private ComponentMaintenance componentMaintenance;

  private OrientDeleteCleanupMethod underTest;

  @Before
  public void setup() throws Exception {
    underTest = new OrientDeleteCleanupMethod(500);

    when(cancelledCheck.getAsBoolean()).thenReturn(false);
    when(repository.facet(ComponentMaintenance.class)).thenReturn(componentMaintenance);
  }
  
  @Test
  public void deleteComponent() throws Exception {
    DeletionProgress deletionProgress = new DeletionProgress();
    deletionProgress.addCount(2L);

    when(componentMaintenance.deleteComponents(any(), any(), anyInt())).thenReturn(deletionProgress);

    DeletionProgress response = underTest.run(repository, ImmutableList.of(component1, component2), cancelledCheck);

    assertThat(response.getCount()).isEqualTo(2L);

    verify(componentMaintenance).deleteComponents(ImmutableList.of(component1, component2), cancelledCheck, BATCH_SIZE);
  }
}
