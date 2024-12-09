package org.sonatype.nexus.transaction;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.inject.Named;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

/**
 * Test {@link Operations} builder methods.
 */
public class OperationsBuilderTest
    extends TestSupport
{
  private interface SampleAnnotations
  {
    @Transactional
    void defaultValues();

    @Transactional(commitOn = IOException.class)
    void customCommitOn();

    @Transactional(retryOn = { InvocationTargetException.class, IllegalStateException.class })
    void customRetryOn();

    @Transactional(swallow = { RuntimeException.class, MalformedURLException.class })
    void customSwallow();

    @Transactional(commitOn = IllegalStateException.class, retryOn = RuntimeException.class, swallow = IOException.class)
    void customValues();

    @Retention(RetentionPolicy.RUNTIME)
    @Transactional(commitOn = IllegalStateException.class, retryOn = RuntimeException.class, swallow = IOException.class)
    @interface Stereotype
    {
      // meta-annotated with @Transactional
    }
  }

  @Test
  public void testBuilderChecksArguments() {

    try {
      new Operations().commitOn(IOException.class, null, RuntimeException.class);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().commitOn((Class<? extends Exception>[]) null);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().retryOn(IOException.class, null, RuntimeException.class);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().retryOn((Class<? extends Exception>[]) null);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().swallow(IOException.class, null, RuntimeException.class);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().swallow((Class<? extends Exception>[]) null);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().stereotype(null);
      fail("Expected NullPointerException");
    }
    catch (NullPointerException e) {
      // expected
    }

    try {
      new Operations().stereotype(Named.class);
      fail("Expected IllegalArgumentException");
    }
    catch (IllegalArgumentException e) {
      // expected
    }
  }

  @Test
  public void testBuilderAnnotionBehaviour() {

    assertBehaviour(
        Operations.DEFAULT_SPEC,
        sample("defaultValues"));

    assertBehaviour(
        new Operations().spec,
        sample("defaultValues"));

    assertBehaviour(
        new Operations().commitOn(IOException.class).spec,
        sample("customCommitOn"));

    assertBehaviour(
        new Operations().retryOn(InvocationTargetException.class, IllegalStateException.class).spec,
        sample("customRetryOn"));

    assertBehaviour(
        new Operations().swallow(RuntimeException.class, MalformedURLException.class).spec,
        sample("customSwallow"));

    assertBehaviour(
        new Operations().commitOn(IllegalStateException.class).retryOn(RuntimeException.class).swallow(IOException.class).spec,
        sample("customValues"));

    assertBehaviour(
        new Operations().stereotype(SampleAnnotations.Stereotype.class).spec,
        sample("customValues"));
  }

  private static void assertBehaviour(final Annotation lhs, final Annotation rhs) {
    assertThat(lhs.equals(null), is(rhs.equals(null)));
    assertThat(lhs.equals(Operations.DEFAULT_SPEC), is(rhs.equals(Operations.DEFAULT_SPEC)));
    assertThat(lhs.equals(rhs), is(rhs.equals(lhs)));
    assertThat(lhs.hashCode(), is(rhs.hashCode()));
    // cope with random order of properties in the JDK's default annotation toString() implementation
    assertThat(lhs.toString().split("[(), ]"), arrayContainingInAnyOrder(rhs.toString().split("[(), ]")));
    assertThat(lhs.annotationType(), is(equalTo(rhs.annotationType())));
  }

  private static Transactional sample(final String name) {
    try {
      return SampleAnnotations.class.getMethod(name).getAnnotation(Transactional.class);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
