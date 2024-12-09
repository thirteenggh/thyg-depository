package org.sonatype.nexus.validation;

import javax.validation.ConstraintViolation;

import org.sonatype.goodies.testsupport.TestSupport;

import com.google.inject.Guice;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConstraintViolationFactoryTest
    extends TestSupport
{
  private static final String JAVA_EL_IMMEDIATE = "${2 + 2}";

  private static final String JAVA_EL_DEFERRED = "{message}";

  private static final String ANY_PATH = "foo";

  private ConstraintViolationFactory cvf;

  @Before
  public void setUp() throws Exception {
    cvf = Guice.createInjector(new ValidationModule()).getInstance(ConstraintViolationFactory.class);
  }

  @Test
  public void shouldStripJavaExpression() {
    // immediate evaluation of JAVA_EL_IMMEDIATE would yield 4, and that would mean we are open to code injection
    ConstraintViolation<?> violation = cvf.createViolation(ANY_PATH, JAVA_EL_IMMEDIATE);
    assertThat(violation.getMessage(), is("{2 + 2}"));
  }

  @Test
  public void shouldEvaluateDeferredExpressionLanguage() {
    // deferred evaluation of JAVA_EL_DEFERRED should yield content of HelperAnnotation.message
    ConstraintViolation<?> violation = cvf.createViolation(ANY_PATH, JAVA_EL_DEFERRED);
    assertThat(violation.getMessage(), is(""));
  }
}
