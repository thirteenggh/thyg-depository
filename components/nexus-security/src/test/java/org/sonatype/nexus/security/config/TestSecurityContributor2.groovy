package org.sonatype.nexus.security.config

import javax.inject.Singleton

import org.sonatype.nexus.security.config.memory.MemoryCPrivilege
import org.sonatype.nexus.security.config.memory.MemoryCRole

@Singleton
class TestSecurityContributor2
implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: '4-test',
                type: 'method',
                name: '4-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path4/'
                ])
            ,
            new MemoryCPrivilege(
                id: '5-test',
                type: 'method',
                name: '5-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path5/'
                ])
            ,
            new MemoryCPrivilege(
                id: '6-test',
                type: 'method',
                name: '6-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path6/'
                ])
        ],
        roles: [
            new MemoryCRole(
                id: 'anon',
                name: '',
                description: '',
                privileges: ['4-test'],
                roles: ['other']
            )
            ,
            new MemoryCRole(
                id: 'other',
                name: 'Other Role',
                description: 'Other Role Description',
                privileges: ['6-test']
            )
        ]
    )
  }
}
