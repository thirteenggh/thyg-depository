package org.sonatype.nexus.repository.npm.internal

import org.sonatype.goodies.testsupport.TestSupport
import org.sonatype.nexus.repository.view.Parameters

import org.joda.time.DateTime
import org.junit.Test

/**
 * UT for {@link NpmHandlers}
 */
class NpmPathsTest
    extends TestSupport
{
  @Test
  void 'Test incremental index request with null params'() {
    DateTime time = NpmPaths.indexSince(null)
    assert time == null
  }

  @Test
  void 'Test incremental index request with no params'() {
    Parameters params = new Parameters()
    DateTime time = NpmPaths.indexSince(params)
    assert time == null
  }

  @Test
  void 'Test incremental index request with valid params'() {
    final DateTime now = DateTime.now()
    Parameters params = new Parameters()
    params.set("stale", "update_after")
    params.set("startkey", String.valueOf(now.millis))
    DateTime time = NpmPaths.indexSince(params)
    assert time == now
  }
}
