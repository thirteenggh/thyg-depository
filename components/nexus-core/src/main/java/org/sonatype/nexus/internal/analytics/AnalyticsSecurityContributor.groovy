package org.sonatype.nexus.internal.analytics

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Analytics security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class AnalyticsSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-analytics-all',
                description: '指标分析的所有权限',
                type: 'application',
                properties: [
                    domain: 'analytics',
                    actions: '*'
                ]
            )
        ]
    )
  }
}

