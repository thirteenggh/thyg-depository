package org.sonatype.nexus.httpclient.config

import javax.validation.ConstraintValidatorContext

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test
import org.mockito.Mock

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo

/**
 * Tests for {@link NonProxyHostsValidator}.
 */
class NonProxyHostsValidatorTest
    extends TestSupport
{
  @Mock
  ConstraintValidatorContext context

  NonProxyHostsValidator validator = new NonProxyHostsValidator()

  private validateAndExpect(String expression, boolean expected) {
    assertThat(validator.isValid([expression] as Set, context), equalTo(expected))
  }

  @Test
  void 'validation positive test'() {
    validateAndExpect('sonatype.org', true)
    validateAndExpect('*.sonatype.org', true)
    validateAndExpect('*.sonatype.*', true)
    validateAndExpect('1.2.3.4', true)
    validateAndExpect('*.2.3.4', true)
    validateAndExpect('1.2.3.*', true)
    validateAndExpect('*.2.3.*', true)
    validateAndExpect('10.*', true)
    validateAndExpect('*.10', true)
    validateAndExpect('csétamás.hu', true)
    validateAndExpect('2001:db8:85a3:8d3:1319:8a2e:370:7348', true)
    validateAndExpect('[2001:db8:85a3:8d3:1319:8a2e:370:7348]', true)
    validateAndExpect('[::1]', true)
    validateAndExpect('*:8]', true)
    validateAndExpect('[:*', true)
    validateAndExpect('localhost', true)
  }

  @Test
  void 'validation negative test'() {
    // these below are the "best effort" we can rule out
    validateAndExpect('', false)
    validateAndExpect('  ', false)
    validateAndExpect('foo|sonatype.org', false)
    validateAndExpect('comma,com', false)
    validateAndExpect('[*:8]', false)
    }
}
