package org.sonatype.nexus.repository.httpbridge.internal.describe

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link DescribeType}.
 */
class DescribeTypeTest
    extends TestSupport
{
  @Test
  void 'parse html'() {
    assert DescribeType.parse('html') == DescribeType.HTML
  }

  @Test
  void 'parse json'() {
    assert DescribeType.parse('json') == DescribeType.JSON
  }

  @Test
  void 'parse blank as html'() {
    assert DescribeType.parse('') == DescribeType.HTML
  }

  @Test
  void 'parse true as html'() {
    assert DescribeType.parse('true') == DescribeType.HTML
  }
}
