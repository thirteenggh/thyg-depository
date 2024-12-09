package org.sonatype.nexus.internal.wonderland

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Wonderland security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class WonderlandSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-wonderland-all',
                description: 'Wonderland 的所有权限',
                type: 'application',
                properties: [
                    domain: 'wonderland',
                    actions: '*'
                ]
            )
        ]
    )
  }
}

