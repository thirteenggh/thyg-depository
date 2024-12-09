package org.sonatype.nexus.security

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.memory.MemoryCUserRoleMapping
import org.sonatype.nexus.security.config.memory.MemoryCUser

/**
 * @since 3.0
 */
class BaseSecurityConfig
{
  static MemorySecurityConfiguration get() {
    return new MemorySecurityConfiguration(
        users: [
            new MemoryCUser(
                id: 'admin',
                password: 'f865b53623b121fd34ee5426c792e5c33af8c227',
                firstName: 'Administrator',
                status: 'active',
                email: 'admin@example.org'
            ),
            new MemoryCUser(
                id: 'deployment',
                password: 'b2a0e378437817cebdf753d7dff3dd75483af9e0',
                firstName: 'Deployment',
                lastName: 'User',
                status: 'active',
                email: 'deployment@example.org'
            ),
            new MemoryCUser(
                id: 'anonymous',
                password: '0a92fab3230134cca6eadd9898325b9b2ae67998',
                firstName: 'Nexus',
                lastName: 'Anonynmous User',
                status: 'active',
                email: 'anonymous@example.org'
            )
        ],
        userRoleMappings: [
            new MemoryCUserRoleMapping(
                userId: 'admin',
                source: 'default',
                roles: ['admin']
            ),
            new MemoryCUserRoleMapping(
                userId: 'deployment',
                source: 'default',
                roles: ['deployment', 'repo-all-full']
            ),
            new MemoryCUserRoleMapping(
                userId: 'anonymous',
                source: 'default',
                roles: ['anonymous', 'repo-all-read']
            )
        ],
    )
  }

}

