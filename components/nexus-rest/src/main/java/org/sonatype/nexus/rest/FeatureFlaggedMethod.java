package org.sonatype.nexus.rest;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.ws.rs.NameBinding;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Flags REST methods that should only respond when the named system property is {@code true}.
 *
 * @since 3.29
 */
@NameBinding
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface FeatureFlaggedMethod
{
  String name() default "";

  boolean enabledByDefault() default false;
}
