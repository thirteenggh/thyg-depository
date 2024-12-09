package org.sonatype.nexus.validation.constraint

import javax.validation.Validation
import javax.validation.Validator

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

import static org.sonatype.nexus.validation.constraint.CaseType.LOWER
import static org.sonatype.nexus.validation.constraint.CaseType.UPPER

/**
 * Tests for {@link Case}.
 */
class CaseTest
    extends TestSupport
{
  private Validator validator

  @Before
  public void setUp() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().validator
  }

  class UperCaseSubject
  {
    @Case(UPPER)
    String value
  }

  @Test
  void 'test UPPER case'() {
    def violations

    violations = validator.validate(new UperCaseSubject(value: 'YES'))
    log violations
    assert violations.size() == 0

    violations = validator.validate(new UperCaseSubject(value: 'No'))
    log violations
    assert violations.size() == 1
  }

  class LowerCaseSubject
  {
    @Case(LOWER)
    String value
  }

  @Test
  void 'test LOWER case'() {
    def violations

    violations = validator.validate(new LowerCaseSubject(value: 'yes'))
    log violations
    assert violations.size() == 0

    violations = validator.validate(new LowerCaseSubject(value: 'No'))
    log violations
    assert violations.size() == 1
  }
}
