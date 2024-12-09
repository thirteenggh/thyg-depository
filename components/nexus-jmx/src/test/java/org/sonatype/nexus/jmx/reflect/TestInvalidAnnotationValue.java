package org.sonatype.nexus.jmx.reflect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.management.DescriptorKey;

/**
 * Helper to test {@link DescriptorKey}.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface TestInvalidAnnotationValue
{
  // DescriptorKey value is not allowed to be an annotation
  @DescriptorKey("invalid")
  TestComments value();
}
