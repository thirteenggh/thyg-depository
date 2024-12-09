package org.sonatype.nexus.security.config

import javax.inject.Singleton

import org.sonatype.nexus.security.config.memory.MemoryCPrivilege
import org.sonatype.nexus.security.config.memory.MemoryCRole

@Singleton
class TestSecurityContributor1
implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'priv1',
                type: 'method',
                name: 'priv1',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': 'priv1-ONE'
                ])
            ,
            new MemoryCPrivilege(
                id: 'priv2',
                type: 'method',
                name: 'priv2',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': 'priv2-TWO'
                ])
            ,
            new MemoryCPrivilege(
                id: 'priv3',
                type: 'method',
                name: 'priv3',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': 'priv3-THREE'
                ])
            ,
            new MemoryCPrivilege(
                id: 'priv4',
                type: 'method',
                name: 'priv4',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': 'priv4-FOUR'
                ])
            ,
            new MemoryCPrivilege(
                id: 'priv5',
                type: 'method',
                name: 'priv5',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': 'priv5-FIVE'
                ])
        ],
        roles: [
            new MemoryCRole(
                id: 'anon',
                name: 'Test Anon Role',
                description: 'Test Anon Role Description',
                privileges: ['priv1'],
                roles: ['role2']
            )
            ,
            new MemoryCRole(
                id: 'other',
                name: '',
                description: '',
                privileges: ['priv2'],
                roles: ['role2']
            )
            ,
            new MemoryCRole(
                id: 'role1',
                name: 'role1',
                description: 'role1',
                privileges: ['priv1'],
                roles: ['role2']
            )
            ,
            new MemoryCRole(
                id: 'role2',
                name: 'role2',
                description: 'role2',
                privileges: ['priv1', 'priv2'],
                roles: ['role3', 'role4', 'role5']
            )
            ,
            new MemoryCRole(
                id: 'role3',
                name: 'role3',
                description: 'role3',
                privileges: ['priv1', 'priv2', 'priv3'],
                roles: ['role4', 'role5']
            )
            ,
            new MemoryCRole(
                id: 'role4',
                name: 'role4',
                description: 'role4',
                privileges: ['priv1', 'priv2', 'priv3', 'priv4']
            )
            ,
            new MemoryCRole(
                id: 'role5',
                name: 'role5',
                description: 'role5',
                privileges: ['priv3', 'priv4', 'priv5']
            )
        ]
    )
  }
}

