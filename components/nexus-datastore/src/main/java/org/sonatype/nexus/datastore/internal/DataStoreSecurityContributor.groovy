package org.sonatype.nexus.datastore.internal

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * DataStore security configuration.
 *
 * @since 3.19
 */
@Named
@Singleton
class DataStoreSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-datastores-all',
                description: '数据存储的所有权限',
                type: 'application',
                properties: [
                    domain : 'datastores',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-datastores-create',
                description: '创建数据存储的权限',
                type: 'application',
                properties: [
                    domain : 'datastores',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-datastores-read',
                description: '读取数据存储的权限',
                type: 'application',
                properties: [
                    domain : 'datastores',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-datastores-update',
                description: '更新数据存储的权限',
                type: 'application',
                properties: [
                    domain : 'datastores',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-datastores-delete',
                description: '删除数据存储的权限',
                type: 'application',
                properties: [
                    domain : 'datastores',
                    actions: 'delete,read'
                ]
            )
        ]
    )
  }
}
