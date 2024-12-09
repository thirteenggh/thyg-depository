package org.sonatype.nexus.security.internal;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rest.ValidationErrorsException;

/**
 * @since 3.25
 */
@Named
@Singleton
public class PasswordValidator
{
  private final Predicate<String> passwordValidator;

  private final String errorMessage;

  @Inject
  public PasswordValidator(
      @Nullable @Named("nexus.password.validator") final String passwordValidator,
      @Nullable @Named("nexus.password.validator.message") final String errorMessage)
  {
    this.passwordValidator =
        Optional.ofNullable(passwordValidator).map(Pattern::compile).map(Pattern::asPredicate).orElse(pw -> true);
    this.errorMessage = errorMessage == null ? "Password does not match corporate policy" : errorMessage;
  }

  public void validate(final String password) {
    if (!passwordValidator.test(password)) {
      throw new ValidationErrorsException(errorMessage);
    }
  }
}
