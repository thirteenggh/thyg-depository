package org.sonatype.nexus.blobstore.group.internal

import org.sonatype.nexus.blobstore.api.BlobStoreMetrics

import spock.lang.Specification

/**
 * {@link BlobStoreGroupMetrics} tests.
 */
class BlobStoreGroupMetricsTest
    extends Specification
{
  def 'Empty metrics is available'() {
    when: 'metrics is empty'
      def metrics = new BlobStoreGroupMetrics([])

    then: 'it is available'
      metrics.unavailable == false
  }

  def 'Metrics with no available member is unavailable'() {
    given:
      BlobStoreMetrics u1 = Mock()
      BlobStoreMetrics u2 = Mock()

    when: 'metrics has two unavailable members'
      def metrics = new BlobStoreGroupMetrics([u1, u2])

    then: 'it is unavailable'
      metrics.unavailable == true
      1 * u1.isUnavailable() >> true
      _ * u1.getAvailableSpaceByFileStore() >> [:]
      1 * u2.isUnavailable() >> true
      _ * u2.getAvailableSpaceByFileStore() >> [:]
  }

  def 'Metrics with one available member is available'() {
    given:
      BlobStoreMetrics u1 = Mock()
      BlobStoreMetrics a1 = Mock()

    when: 'metrics has one available member and one unavailable member'
      def metrics = new BlobStoreGroupMetrics([u1, a1])

    then: 'it is available'
      metrics.unavailable == false
      1 * u1.isUnavailable() >> true
      _ * u1.getAvailableSpaceByFileStore() >> [:]
      1 * a1.isUnavailable() >> false
      _ * a1.getAvailableSpaceByFileStore() >> [:]
  }
}
