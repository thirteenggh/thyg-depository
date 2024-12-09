package org.sonatype.nexus.repository.storage.internal

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.common.entity.EntityMetadata
import org.sonatype.nexus.common.event.EventManager
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.manager.RepositoryManager
import org.sonatype.nexus.repository.manager.RepositoryMetadataUpdatedEvent

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.instanceOf
import static org.mockito.Matchers.isA
import static org.mockito.Mockito.atLeastOnce
import static org.mockito.Mockito.never
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

class BucketEventInspectorTest
    extends TestSupport
{
  static final String MAVEN_CENTRAL_NAME = 'maven-central'

  @Mock
  RepositoryManager repositoryManager

  @Mock
  Repository mavenCentralRepository

  @Mock
  EventManager eventManager

  @Mock
  EntityMetadata entityMetadata

  BucketEventInspector underTest

  @Before
  void setup() {
    underTest = new BucketEventInspector(repositoryManager, eventManager)
  }

  @Test
  void 'post metadata-updated-event when bucket of existing repository is updated'() {
    when(repositoryManager.get(MAVEN_CENTRAL_NAME)).thenReturn(mavenCentralRepository)
    BucketUpdatedEvent bucketEvent = new BucketUpdatedEvent(entityMetadata, MAVEN_CENTRAL_NAME)
    underTest.onBucketUpdated(bucketEvent)
    ArgumentCaptor<Object> eventCaptor = ArgumentCaptor.forClass(Object)
    verify(eventManager, atLeastOnce()).post(eventCaptor.capture())
    Object repoEvent = eventCaptor.allValues.last()
    assertThat(repoEvent, is(instanceOf(RepositoryMetadataUpdatedEvent)))
    assertThat(repoEvent.repository, is(mavenCentralRepository))
  }

  @Test
  void 'do not post metadata-updated-event when bucket of deleted repository is updated'() {
    BucketUpdatedEvent bucketEvent = new BucketUpdatedEvent(entityMetadata, 'some-deleted-repo$uuid')
    underTest.onBucketUpdated(bucketEvent)
    verify(eventManager, never()).post(isA(RepositoryMetadataUpdatedEvent))
  }
}
