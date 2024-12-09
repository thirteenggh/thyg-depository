package org.sonatype.nexus.internal.metrics

import org.sonatype.nexus.common.app.FreezeRequest
import org.sonatype.nexus.common.app.FreezeService

import org.joda.time.DateTime
import spock.lang.Specification

class ReadOnlyMetricSetTest
    extends Specification {

  FreezeService freezeService = Mock()

  def 'default metrics generated when no freeze service available'() {
    when:
      ReadOnlyMetricSet readOnlyMetricSet = new ReadOnlyMetricSet({ -> null})
      def metrics = readOnlyMetricSet.metrics.collectEntries { name, metric ->
          [name, metric.value]
      }

    then:
      metrics.enabled == false 
      metrics.pending == 0
      metrics.freezeTime == 0L
  }

  def 'false metrics generated when not in read only mode'() {
    when:
      ReadOnlyMetricSet readOnlyMetricSet = new ReadOnlyMetricSet({ -> freezeService})
      def metrics = readOnlyMetricSet.metrics.collectEntries { name, metric ->
        [name, metric.value]
      }

    then:
      1 * freezeService.isFrozen() >> false
      2 * freezeService.currentFreezeRequests() >> []
      metrics.enabled == false 
      metrics.pending == 0
      metrics.freezeTime == 0L
  }

  def 'expected metrics generated when in read only mode'() {
    given:
      def freezeRequests = [
        new FreezeRequest('SYSTEM', "system initiator", new DateTime(1504111817165), null, null),
        new FreezeRequest('USER', "user initiator", new DateTime(1504111817166), null, null)
      ]

    when:
      ReadOnlyMetricSet readOnlyMetricSet = new ReadOnlyMetricSet({ -> freezeService})
      def metrics = readOnlyMetricSet.metrics.collectEntries { name, metric ->
        [name, metric.value]
      }

    then:
      1 * freezeService.isFrozen() >> true
      2 * freezeService.currentFreezeRequests() >> freezeRequests
      metrics.enabled == true 
      metrics.pending == 2
      metrics.freezeTime == 1504111817165
  }
}
