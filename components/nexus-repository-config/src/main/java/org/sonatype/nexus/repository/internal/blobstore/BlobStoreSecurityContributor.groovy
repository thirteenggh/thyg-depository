package org.sonatype.nexus.repository.internal.blobstore

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * BlobStore security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class BlobStoreSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-blobstores-all',
                description: 'Blob 存储的所有权限',
                type: 'application',
                properties: [
                    domain : 'blobstores',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-blobstores-create',
                description: '创建 Blob 存储的权限',
                type: 'application',
                properties: [
                    domain : 'blobstores',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-blobstores-read',
                description: '读取 Blob 存储的权限',
                type: 'application',
                properties: [
                    domain : 'blobstores',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-blobstores-update',
                description: '更新 Blob 存储的权限',
                type: 'application',
                properties: [
                    domain : 'blobstores',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-blobstores-delete',
                description: '删除 Blob 存储的权限',
                type: 'application',
                properties: [
                    domain : 'blobstores',
                    actions: 'delete,read'
                ]
            )
        ]
    )
  }
}
