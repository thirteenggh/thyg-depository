package org.sonatype.nexus.repository.config;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validate a name as unique across all Repositories.
 *
 * @since 3.0
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueRepositoryNameValidator.class)
public @interface UniqueRepositoryName
{
  String message() default "Name is already used, must be unique (ignoring case)";

  Class<?>[] groups() default { };

  Class<? extends Payload>[] payload() default { };
}
