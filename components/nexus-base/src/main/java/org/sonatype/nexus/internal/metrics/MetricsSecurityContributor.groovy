package org.sonatype.nexus.internal.metrics

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Metrics security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class MetricsSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-metrics-all',
                description: 'Metrics 的所有权限',
                type: 'application',
                properties: [
                    domain: 'metrics',
                    actions: '*'
                ]
            )
        ]
    )
  }
}

