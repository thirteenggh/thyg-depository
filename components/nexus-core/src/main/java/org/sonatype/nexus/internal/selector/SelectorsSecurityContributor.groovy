package org.sonatype.nexus.internal.selector

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Selectors security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class SelectorsSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-selectors-all',
                description: '内容选择器的所有权限',
                type: 'application',
                properties: [
                    domain : 'selectors',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-selectors-create',
                description: '内容选择器的创建权限',
                type: 'application',
                properties: [
                    domain : 'selectors',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-selectors-read',
                description: '内容选择器的读取权限',
                type: 'application',
                properties: [
                    domain : 'selectors',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-selectors-update',
                description: '内容选择器的更新权限',
                type: 'application',
                properties: [
                    domain : 'selectors',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-selectors-delete',
                description: '内容选择器的删除权限',
                type: 'application',
                properties: [
                    domain : 'selectors',
                    actions: 'delete,read'
                ]
            )
        ]
    )
  }
}

