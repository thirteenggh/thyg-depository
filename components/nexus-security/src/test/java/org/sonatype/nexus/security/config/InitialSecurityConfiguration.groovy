package org.sonatype.nexus.security.config

import org.sonatype.nexus.security.config.memory.MemoryCPrivilege
import org.sonatype.nexus.security.config.memory.MemoryCRole

/**
 * @since 3.0
 */
class InitialSecurityConfiguration
{

  static MemorySecurityConfiguration getConfiguration() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: '1-test',
                type: 'method',
                name: '1-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path/'
                ]
            ),
            new MemoryCPrivilege(
                id: '2-test',
                type: 'method',
                name: '2-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path/'
                ]
            )
        ],
        roles: [
            new MemoryCRole(
                id: 'test',
                name: 'test Role',
                description: 'Test Role Description',
                privileges: ['2-test']
            )
        ]
    )
  }

}

