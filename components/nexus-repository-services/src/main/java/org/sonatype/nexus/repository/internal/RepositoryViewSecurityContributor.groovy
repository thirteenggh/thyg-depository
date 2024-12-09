package org.sonatype.nexus.repository.internal

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Repository view security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class RepositoryViewSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            //
            // nexus:repository-view
            //

            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-*',
                description: '所有存储库视图的所有权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-browse',
                description: '所有存储库视图的浏览权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: 'browse'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-read',
                description: '所有存储库视图的读取权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-edit',
                description: '所有存储库视图的编辑权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: 'edit'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-add',
                description: '所有存储库视图的添加权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: 'add'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-repository-view-*-*-delete',
                description: '所有存储库视图的删除权限',
                type: 'repository-view',
                properties: [
                    format: '*',
                    repository: '*',
                    actions: 'delete'
                ]
            ),
        ]
    )
  }
}

