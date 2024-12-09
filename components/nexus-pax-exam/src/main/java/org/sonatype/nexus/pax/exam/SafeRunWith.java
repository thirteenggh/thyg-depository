package org.sonatype.nexus.pax.exam;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.runner.Runner;

/**
 * Specifies the {@link Runner} that {@link SafeRunner} should delegate to.
 *
 * @since 3.14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SafeRunWith
{
  Class<? extends Runner> value();
}
