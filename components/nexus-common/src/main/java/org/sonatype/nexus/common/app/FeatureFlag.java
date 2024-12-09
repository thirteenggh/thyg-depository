package org.sonatype.nexus.common.app;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Flags packages or components that should only exist when the named system property is {@code true}.
 *
 * @since 3.19
 */
@Retention(RUNTIME)
@Target({ PACKAGE, TYPE })
public @interface FeatureFlag
{
  String name();

  boolean enabledByDefault() default false;
}
