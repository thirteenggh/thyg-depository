package org.sonatype.nexus.jmx.reflect

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link org.sonatype.nexus.jmx.reflect.DescriptorHelper}
 */
class DescriptorHelperTest
    extends TestSupport
{
  @TestAuthor('jason')
  class TestBean
  {
    @TestComments('foo bar baz')
    def foo() {
      // empty
    }

    @TestInvalidAnnotationValue(@TestComments('foo'))
    def invalid1() {
      // empty
    }
  }

  @Test
  void 'finds annotations'() {
    def bean = new TestBean()
    def annotations = DescriptorHelper.findAllAnnotations(bean.getClass().annotations)
    log annotations

    // custom annotation should be found
    assert annotations.find { it.annotationType().name == TestAuthor.class.name}
  }

  @Test
  void 'build descriptor from type'() {
    def bean = new TestBean()
    def descriptor = DescriptorHelper.build(bean.getClass())
    log descriptor

    // descriptor should have author
    assert descriptor.fields.length == 1
    assert descriptor.getFieldValue('author') == 'jason'
  }

  @Test
  void 'build descriptor from method'() {
    def method = TestBean.class.getMethod('foo')
    def descriptor = DescriptorHelper.build(method)
    log descriptor

    // descriptor should have comments
    assert descriptor.fields.length == 1
    assert descriptor.getFieldValue('comments') == 'foo bar baz'
  }

  @Test(expected = DescriptorHelper.InvalidDescriptorKeyException.class)
  void 'build descriptor fails due to invalid'() {
    def method = TestBean.class.getMethod('invalid1')
    def descriptor = DescriptorHelper.build(method)
    log descriptor
  }
}

