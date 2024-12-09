package org.sonatype.nexus.jmx.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Named;

import org.sonatype.nexus.jmx.ObjectNameEntry;

/**
 * Marks a component for JMX management.
 *
 * @see ManagedAttribute
 * @see ManagedOperation
 * @since 3.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ManagedObject
{
  /**
   * Customized object-name domain.
   *
   * If unset will default to the package-name of the component.
   */
  String domain() default "";

  /**
   * Customized object-name 'type' entry.
   *
   * If unset will consider {@link #typeClass} or default to simple-name of component class.
   *
   * @see #typeClass
   */
  String type() default "";

  /**
   * Customized object-name 'type' entry, class-reference form.
   *
   * @see #type
   */
  Class<?> typeClass() default Void.class;

  /**
   * Customized object-name 'name' entry.
   *
   * If unset will default to {@link Named} value.
   */
  String name() default "";

  /**
   * Additional entries to customize object-name.
   */
  ObjectNameEntry[] entries() default {};

  /**
   * Optional description for MBean.
   */
  String description() default "";
}
