package org.sonatype.nexus.security.config

import org.sonatype.nexus.security.config.memory.MemoryCPrivilege
import org.sonatype.nexus.security.config.memory.MemoryCRole

class TestSecurityContributor3
implements SecurityContributor
{

  private static int INSTANCE_COUNT = 1

  private String privId = "priv-" + INSTANCE_COUNT++

  public String getId() {
    return privId
  }

  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: '4-test-' + privId,
                type: 'method',
                name: '4-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path4/'
                ])
            ,
            new MemoryCPrivilege(
                id: '5-test-' + privId,
                type: 'method',
                name: '5-test',
                description: '',
                properties: [
                    'method': 'read',
                    'permission': '/some/path5/'
                ])
            ,
            new MemoryCPrivilege(
                id: '6-test-' + privId,
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
