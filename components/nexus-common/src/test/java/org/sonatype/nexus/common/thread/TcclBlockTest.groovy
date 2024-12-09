package org.sonatype.nexus.common.thread

import java.security.SecureClassLoader

import org.sonatype.goodies.testsupport.TestSupport

import org.junit.Test

/**
 * Tests for {@link TcclBlock}.
 */
class TcclBlockTest
    extends TestSupport
{
  @Test
  void 'begin and restore class-loader'() {
    Thread thread = Thread.currentThread()
    ClassLoader original = thread.contextClassLoader
    ClassLoader classLoader = new SecureClassLoader(getClass().classLoader) {}

    def tccl = TcclBlock.begin(classLoader)
    try {
      assert thread.contextClassLoader.is(classLoader)
    }
    finally {
      tccl.close()
    }
    assert thread.contextClassLoader.is(original)
  }
}
