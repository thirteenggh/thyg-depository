package org.sonatype.nexus.internal.log

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Logging security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class LoggingSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-logging-all',
                description: '日志的所有权限',
                type: 'application',
                properties: [
                    domain: 'logging',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-logging-read',
                description: '读取日志的权限',
                type: 'application',
                properties: [
                    domain: 'logging',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-logging-update',
                description: '更新日志的权限',
                type: 'application',
                properties: [
                    domain: 'logging',
                    actions: 'update'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-logging-mark',
                description: '标记日志的权限',
                type: 'application',
                properties: [
                    domain: 'logging',
                    actions: 'create'
                ]
            ),
        ]
    )
  }
}

