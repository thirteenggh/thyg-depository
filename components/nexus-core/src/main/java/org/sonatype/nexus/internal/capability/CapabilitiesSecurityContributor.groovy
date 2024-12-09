package org.sonatype.nexus.internal.capability

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

// FIXME: normalize names to use capability instead of capabilities

/**
 * Capabilities security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class CapabilitiesSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-capabilities-all',
                description: '功能的所有权限',
                type: 'application',
                properties: [
                    domain : 'capabilities',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-capabilities-create',
                description: '创建功能的权限',
                type: 'application',
                properties: [
                    domain : 'capabilities',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-capabilities-read',
                description: '读取功能的权限',
                type: 'application',
                properties: [
                    domain : 'capabilities',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-capabilities-update',
                description: '更新功能的权限',
                type: 'application',
                properties: [
                    domain : 'capabilities',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-capabilities-delete',
                description: '删除功能的权限',
                type: 'application',
                properties: [
                    domain : 'capabilities',
                    actions: 'delete,read'
                ]
            )
        ]
    )
  }
}

