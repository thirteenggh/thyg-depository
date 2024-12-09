package org.sonatype.nexus.internal.atlas

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Atlas security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class AtlasSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-atlas-all',
                description: '支持工具的所有权限',
                type: 'application',
                properties: [
                    domain : 'atlas',
                    actions: '*'
                ]
            )
        ]
    )
  }
}

