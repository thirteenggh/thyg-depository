package org.sonatype.nexus.coreui;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.sonatype.goodies.testsupport.inject.InjectedTestSupport;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.selector.CselSelector;
import org.sonatype.nexus.selector.SelectorConfiguration;
import org.sonatype.nexus.selector.SelectorManager;
import org.sonatype.nexus.validation.ConstraintViolationFactory;

import com.google.inject.Binder;
import org.eclipse.sisu.space.BeanScanning;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests {@link SelectorComponent}.
 */
public class SelectorComponentTest
    extends InjectedTestSupport
{
  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Inject
  private SelectorComponent component;

  private Path path = mock(Path.class);

  private SelectorManager mockSelectorManager = mock(SelectorManager.class);

  @Override
  public void configure(final Binder binder) {
    ConstraintViolationFactory constraintViolationFactory = mock(ConstraintViolationFactory.class);
    ConstraintViolation constraintViolation = mock(ConstraintViolation.class);
    SecuritySystem securitySystem = mock(SecuritySystem.class);
    binder.bind(ConstraintViolationFactory.class).toInstance(constraintViolationFactory);
    binder.bind(SelectorManager.class).toInstance(mockSelectorManager);
    binder.bind(SecuritySystem.class).toInstance(securitySystem);
    when(constraintViolationFactory.createViolation(eq("expression"), anyString())).thenReturn(constraintViolation);
    when(constraintViolation.getPropertyPath()).thenReturn(path);
  }

  @Override
  public BeanScanning scanning() {
    return BeanScanning.INDEX;
  }

  @Test
  public void testCreateJexl_invalidExpression() {
    SelectorXO xo = new SelectorXO();
    xo.setExpression("a ==== b");
    xo.setType("jexl");

    try {
      component.create(xo);
      fail();
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().size(), is(1));
      assertThat(e.getConstraintViolations().iterator().next().getPropertyPath(), is(path));
    }
  }

  @Test
  public void testCreateCsel_invalidExpression() {
    SelectorXO xo = new SelectorXO();
    xo.setExpression("a ==== b");
    xo.setType(CselSelector.TYPE);

    try {
      component.create(xo);
      fail();
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().size(), is(1));
      assertThat(e.getConstraintViolations().iterator().next().getPropertyPath(), is(path));
    }
  }

  @Test
  public void testUpdateJexl_invalidExpression() {
    SelectorXO xo = new SelectorXO();
    xo.setExpression("a ==== b");
    xo.setType("jexl");

    try {
      component.update(xo);
      fail();
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().size(), is(1));
      assertThat(e.getConstraintViolations().iterator().next().getPropertyPath(), is(path));
    }
  }

  @Test
  public void testUpdateCsel_invalidExpression() {
    SelectorXO xo = new SelectorXO();
    xo.setExpression("a ==== b");
    xo.setType(CselSelector.TYPE);

    try {
      component.update(xo);
      fail();
    }
    catch (ConstraintViolationException e) {
      assertThat(e.getConstraintViolations().size(), is(1));
      assertThat(e.getConstraintViolations().iterator().next().getPropertyPath(), is(path));
    }
  }

  @Test
  public void testDelete_blobStoreInUse() {
    when(mockSelectorManager.readByName(any())).thenReturn(mock(SelectorConfiguration.class));
    doThrow(new IllegalStateException("a message")).when(mockSelectorManager).delete(any());

    expectedException.expect(ConstraintViolationException.class);
    expectedException.expectMessage("a message");

    component.remove("someSelector");
  }
}
