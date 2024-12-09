package com.sonatype.nexus.ssl.plugin.internal

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * SSL security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class SslSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-ssl-truststore-all',
                description: 'SSL Truststore的所有权限',
                type: 'application',
                properties: [
                    domain : 'ssl-truststore',
                    actions: '*',
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-ssl-truststore-read',
                description: 'SSL Truststore的读取权限',
                type: 'application',
                properties: [
                    domain : 'ssl-truststore',
                    actions: 'read',
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-ssl-truststore-create',
                description: 'SSL Truststore的创建权限',
                type: 'application',
                properties: [
                    domain : 'ssl-truststore',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-ssl-truststore-update',
                description: 'SSL Truststore的更新权限',
                type: 'application',
                properties: [
                    domain : 'ssl-truststore',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-ssl-truststore-delete',
                description: 'SSL Truststore的删除权限',
                type: 'application',
                properties: [
                    domain : 'ssl-truststore',
                    actions: 'delete,read'
                ]
            )
        ]
    )
  }
}

