package org.sonatype.nexus.repository.pypi.internal

import spock.lang.Specification
import spock.lang.Unroll

/**
 * {@link PyPiPathUtils} unit tests.
 */
class PyPiPathUtilsTest
    extends Specification
{
  @Unroll
  def 'Normalize name #initialName following the PEP 503 standard'() {
    expect:
      PyPiPathUtils.normalizeName(initialName) == normalizedName
    where:
      initialName  | normalizedName
      'some-name'  | 'some-name'
      'Some_Name'  | 'some-name'
      'some_name'  | 'some-name'
      'some.name'  | 'some-name'
      'some--name' | 'some-name'
      'some__name' | 'some-name'
      'some..name' | 'some-name'
  }
}
