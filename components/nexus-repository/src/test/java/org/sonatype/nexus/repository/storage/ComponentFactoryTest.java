package org.sonatype.nexus.repository.storage;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComponentFactoryTest
    extends TestSupport
{
  @Mock
  private ComponentDecorator componentDecorator;

  private ComponentFactory underTest;

  @Before
  public void setup() {
    underTest = new ComponentFactory(ImmutableSet.of(componentDecorator));

    Component component = new DefaultComponent();
    when(componentDecorator.decorate(any(Component.class))).thenReturn(new TestComponent(component));
  }

  @Test
  public void testComponent() {
    Component component = underTest.createComponent();
    assertThat(component, notNullValue());
    assertThat(component, instanceOf(TestComponent.class));
    verify(componentDecorator).decorate(any(Component.class));
    TestComponent testComponent = (TestComponent) component;
    assertThat(testComponent.getWrappedObject(), instanceOf(DefaultComponent.class));
  }

  private class TestComponent
      extends DecoratedComponent
      implements Component
  {
    TestComponent(final Component component) {
      super(component);
    }
  }
}
