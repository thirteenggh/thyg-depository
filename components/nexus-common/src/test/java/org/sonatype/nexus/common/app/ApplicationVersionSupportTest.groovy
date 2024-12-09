package org.sonatype.nexus.common.app

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Before
import org.junit.Test

/**
 * Tests for {@link ApplicationVersionSupport}.
 */
class ApplicationVersionSupportTest
    extends TestSupport
{
  ApplicationVersionSupport underTest

  Properties props

  @Before
  void setUp() {
    props = new Properties()
    underTest = new ApplicationVersionSupport() {
      @Override
      def Properties getProperties() {
        return props
      }

      @Override
      String getEdition() {
        return 'TEST'
      }
    }
  }

  @Test
  void 'edition returns non-UNKNOWN'() {
    assert underTest.edition == 'TEST'
  }

  @Test
  void 'version returns value'() {
    props[ApplicationVersionSupport.VERSION] = '123'
    assert underTest.version == '123'
  }

  @Test
  void 'missing version returns UNKNOWN'() {
    assert underTest.version == ApplicationVersionSupport.UNKNOWN
  }
}
