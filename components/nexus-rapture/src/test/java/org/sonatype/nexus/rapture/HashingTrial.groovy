package org.sonatype.nexus.rapture

import java.nio.charset.StandardCharsets

import org.sonatype.goodies.testsupport.TestSupport

import com.google.common.hash.Hashing
import com.google.gson.GsonBuilder
import org.junit.Test

/**
 * Hashing trials.
 */
class HashingTrial
    extends TestSupport
{
  @Test
  void 'test gson hash'() {
    def gson = new GsonBuilder().create()
    def data = ['a', 'b', 'c']
    def hash = Hashing.sha1().hashString(gson.toJson(data), StandardCharsets.UTF_8).toString()
    log hash
    assert hash == 'e13460afb1e68af030bb9bee8344c274494661fa'
  }

  @Test
  void 'test object hash'() {
    def data = ['a', 'b', 'c']
    log data.hashCode()
    data << 'd'
    log data.hashCode()
  }
}
