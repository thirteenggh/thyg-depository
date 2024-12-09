package org.sonatype.nexus.validation.constraint

import javax.validation.Validation
import javax.validation.Validator

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

/**
 * Tests for {@link PortNumber}.
 */
class PortNumberTest
    extends TestSupport
{
  private Validator validator

  @Before
  public void setUp() throws Exception {
    validator = Validation.buildDefaultValidatorFactory().validator
  }

  class MaxPortSubject
  {
    @PortNumber(max = 100)
    Integer value
  }

  @Test
  void 'test max port'() {
    def violations

    violations = validator.validate(new MaxPortSubject(value: 100))
    log violations
    assert violations.size() == 0

    violations = validator.validate(new MaxPortSubject(value: 101))
    log violations
    assert violations.size() == 1
  }
}
