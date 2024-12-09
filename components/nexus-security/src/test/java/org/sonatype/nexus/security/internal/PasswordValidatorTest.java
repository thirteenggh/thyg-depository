
package org.sonatype.nexus.security.internal;

import org.sonatype.nexus.rest.ValidationErrorsException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @since 3.25
 */
public class PasswordValidatorTest
{
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testValidate_noValidation() {
    PasswordValidator underTest = new PasswordValidator(null, null);

    underTest.validate("foo");
  }

  @Test
  public void testValidate_passValidation() {
    PasswordValidator underTest = new PasswordValidator(".*", null);

    underTest.validate("foo");
  }

  @Test
  public void testValidate_failValidation() {
    PasswordValidator underTest = new PasswordValidator("[a]+", null);

    thrown.expect(ValidationErrorsException.class);
    thrown.expectMessage("Password does not match corporate policy");

    underTest.validate("foo");
  }

  @Test
  public void testValidate_failValidationCustomErrorMessage() {
    PasswordValidator underTest = new PasswordValidator("[a]+", "Bad bad bad");

    thrown.expect(ValidationErrorsException.class);
    thrown.expectMessage("Bad bad bad");

    underTest.validate("foo");
  }
}
