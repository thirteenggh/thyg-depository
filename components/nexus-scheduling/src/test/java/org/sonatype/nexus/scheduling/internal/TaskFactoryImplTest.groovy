package org.sonatype.nexus.scheduling.internal

import java.lang.annotation.Annotation

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.scheduling.TaskConfiguration

import com.google.inject.Key
import com.google.inject.util.Providers
import org.eclipse.sisu.BeanEntry
import org.eclipse.sisu.inject.BeanLocator
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import static org.junit.Assert.fail
import static org.mockito.Matchers.any
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 * Tests for {@link TaskFactoryImpl}.
 */
class TaskFactoryImplTest
    extends TestSupport
{
  @Mock
  private BeanLocator beanLocator

  private TaskFactoryImpl underTest

  @Before
  void setUp() {
    BeanEntry<Annotation, SimpleTask> simpleTaskBeanEntry = mock(BeanEntry.class)
    when(simpleTaskBeanEntry.getImplementationClass()).thenReturn(SimpleTask)
    when(simpleTaskBeanEntry.getProvider()).thenReturn(Providers.of(new SimpleTask()))
    when(beanLocator.locate(any(Key))).thenReturn(Collections.singletonList(simpleTaskBeanEntry))
    underTest = new TaskFactoryImpl(beanLocator)
  }

  @Test
  void 'register and find descriptor'() {
    assert underTest.descriptors.isEmpty()

    underTest.addDescriptor(new SimpleTaskDescriptor())
    assert underTest.descriptors.size() == 1

    def descriptor1 = underTest.findDescriptor(SimpleTaskDescriptor.TYPE_ID)
    assert descriptor1 != null

    def descriptor2 = underTest.findDescriptor('no-such-type-id')
    assert descriptor2 == null
  }

  @Test
  void 'descriptor list is immutable'() {
    // can not add directly to the list
    try {
      underTest.descriptors.add(new SimpleTaskDescriptor())
      fail()
    }
    catch (UnsupportedOperationException e) {
      // expected
    }

    // can not remove from list
    underTest.addDescriptor(new SimpleTaskDescriptor())
    try {
      underTest.descriptors.iterator().remove()
      fail()
    }
    catch (UnsupportedOperationException e) {
      // expected
    }
  }

  @Test
  void 'create missing descriptor'() {
    def config = new TaskConfiguration(
        id: UUID.randomUUID().toString(),
        typeId: 'no-such-type-id'
    )
    try {
      underTest.create(config)
      fail()
    }
    catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  void 'create task'() {
    underTest.addDescriptor(new SimpleTaskDescriptor())

    def config = new TaskConfiguration(
        id: UUID.randomUUID().toString(),
        typeId: SimpleTaskDescriptor.TYPE_ID
    )
    def task = underTest.create(config)

    assert task != null
    assert task instanceof SimpleTask
  }
}
