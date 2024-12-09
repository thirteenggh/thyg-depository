
package org.sonatype.nexus.rapture.internal.state

import org.sonatype.nexus.rapture.internal.HealthCheckCacheManager

import com.codahale.metrics.health.HealthCheck.Result
import spock.lang.Specification

import static org.sonatype.nexus.rapture.internal.state.HealthCheckStateContributor.HC_FAILED_KEY

class HealthCheckStateContributorTest
    extends Specification
{
  def "It will return an indicator if any health checks have failed"() {
    given:
      def healthCheckCacheManager = Mock(HealthCheckCacheManager) {
        getResults() >> [
            a: Result.healthy(),
            b: result,
            c: Result.healthy()
        ]
      }
      def subject = new HealthCheckStateContributor(healthCheckCacheManager)

    when:
      def state = subject.state

    then:
      state[HC_FAILED_KEY] == expectedState

    where:
      expectedState | result
      true          | Result.unhealthy('Not healthy')
      false         | Result.healthy()
  }
}
