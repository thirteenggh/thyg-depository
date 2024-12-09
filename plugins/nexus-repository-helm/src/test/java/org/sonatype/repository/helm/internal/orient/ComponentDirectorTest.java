package org.sonatype.repository.helm.internal.orient;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.BucketStore;
import org.sonatype.nexus.repository.storage.Component;

import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComponentDirectorTest
    extends TestSupport
{
  @Mock
  private BucketStore bucketStore;

  @Mock
  private RepositoryManager repositoryManager;

  @Mock
  private Component component;

  @Mock
  private Repository source;

  @Mock
  private Repository destination;

  @Test
  public void allowMoveTest() {
    HelmComponentDirector director = new HelmComponentDirector(bucketStore, repositoryManager);
    assertTrue(director.allowMoveTo(destination));
    assertTrue(director.allowMoveFrom(source));

    EntityId bucketId = mock(EntityId.class);
    when(component.bucketId()).thenReturn(bucketId);
    Bucket bucket = mock(Bucket.class);
    when(bucketStore.getById(bucketId)).thenReturn(bucket);
    when(bucket.getRepositoryName()).thenReturn("repo");
    when(repositoryManager.get("repo")).thenReturn(source);

    assertTrue(director.allowMoveTo(component, destination));

    assertTrue(director.allowMoveTo(destination));
  }
}
