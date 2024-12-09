package org.sonatype.nexus.transaction;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Example @{@link Transactional} stereotype annotation.
 */
@Transactional(retryOn = IOException.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnIOException
{
  // empty
}
