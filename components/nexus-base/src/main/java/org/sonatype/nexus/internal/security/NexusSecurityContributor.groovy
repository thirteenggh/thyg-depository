package org.sonatype.nexus.internal.security

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.Roles
import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege
import org.sonatype.nexus.security.config.memory.MemoryCRole

/**
 * Default Nexus security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class NexusSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            /**
            * 授予对 'nexus:' 命名空间中所有内容的权限。
            */
            new MemoryCPrivilege(
                id: 'nx-all',
                type: 'wildcard',
                description: '所有权限', // All permissions
                properties: [
                    pattern: 'nexus:*'
                ]
            ),

            //
            // nexus:settings
            //

            new MemoryCPrivilege(
                id: 'nx-settings-all',
                description: '设置的所有权限', // All permissions for Settings
                type: 'application',
                properties: [
                    domain : 'settings',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-settings-read',
                description: '读取设置的权限', // Read permission for Settings
                type: 'application',
                properties: [
                    domain : 'settings',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-settings-update',
                description: '更新设置的权限', // Update permission for Settings
                type: 'application',
                properties: [
                    domain : 'settings',
                    actions: 'update,read'
                ]
            ),

            //
            // nexus:bundles
            //

            new MemoryCPrivilege(
                id: 'nx-bundles-all',
                description: '捆绑包的所有权限', // All permissions for Bundles
                type: 'application',
                properties: [
                    domain : 'bundles',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-bundles-read',
                description: '读取捆绑包的权限', // Read permission for Bundles
                type: 'application',
                properties: [
                    domain : 'bundles',
                    actions: 'read'
                ]
            ),

            //
            // nexus:search
            //

            new MemoryCPrivilege(
                id: 'nx-search-read',
                description: '读取搜索的权限', // Read permission for Search
                type: 'application',
                properties: [
                    domain : 'search',
                    actions: 'read'
                ]
            ),

            //
            // nexus:apikey
            //

            new MemoryCPrivilege(
                id: 'nx-apikey-all',
                description: 'APIKey 的所有权限', // All permissions for APIKey
                type: 'application',
                properties: [
                    domain : 'apikey',
                    actions: '*'
                ]
            ),

            //
            // nexus:privileges
            //

            new MemoryCPrivilege(
                id: 'nx-privileges-all',
                description: '权限的所有权限', // All permissions for Privileges
                type: 'application',
                properties: [
                    domain : 'privileges',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-privileges-create',
                description: '创建权限的权限', // Create permission for Privileges
                type: 'application',
                properties: [
                    domain : 'privileges',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-privileges-read',
                description: '读取权限的权限', // Read permission for Privileges
                type: 'application',
                properties: [
                    domain : 'privileges',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-privileges-update',
                description: '更新权限的权限', // Update permission for Privileges
                type: 'application',
                properties: [
                    domain : 'privileges',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-privileges-delete',
                description: '删除权限的权限', // Delete permission for Privileges
                type: 'application',
                properties: [
                    domain : 'privileges',
                    actions: 'delete,read'
                ]
            ),

            //
            // nexus:roles
            //

            new MemoryCPrivilege(
                id: 'nx-roles-all',
                description: '角色的所有权限', // All permissions for Roles
                type: 'application',
                properties: [
                    domain : 'roles',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-roles-create',
                description: '创建角色的权限', // Create permission for Roles
                type: 'application',
                properties: [
                    domain : 'roles',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-roles-read',
                description: '读取角色的权限', // Read permission for Roles
                type: 'application',
                properties: [
                    domain : 'roles',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-roles-update',
                description: '更新角色的权限', // Update permission for Roles
                type: 'application',
                properties: [
                    domain : 'roles',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-roles-delete',
                description: '删除角色的权限', // Delete permission for Roles
                type: 'application',
                properties: [
                    domain : 'roles',
                    actions: 'delete,read'
                ]
            ),

            //
            // nexus:users
            //

            new MemoryCPrivilege(
                id: 'nx-users-all',
                description: '用户的所有权限', // All permissions for Users
                type: 'application',
                properties: [
                    domain : 'users',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-users-create',
                description: '创建用户的权限', // Create permission for Users
                type: 'application',
                properties: [
                    domain : 'users',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-users-read',
                description: '读取用户的权限', // Read permission for Users
                type: 'application',
                properties: [
                    domain : 'users',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-users-update',
                description: '更新用户的权限', // Update permission for Users
                type: 'application',
                properties: [
                    domain : 'users',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-users-delete',
                description: '删除用户的权限', // Delete permission for Users
                type: 'application',
                properties: [
                    domain : 'users',
                    actions: 'delete,read'
                ]
            ),

            // FIXME: 确定此独立权限的使用场景，考虑 nexus:users:change-password？
            new MemoryCPrivilege(
                id: 'nx-userschangepw',
                description: '修改密码的权限', // Change password permission
                type: 'application',
                properties: [
                    domain : 'userschangepw',
                    actions: 'create,read'
                ]
            ),

            // nexus:component

            new MemoryCPrivilege(
                id: 'nx-component-upload',
                description: '上传组件的权限', // Upload component permission
                type: 'application',
                properties: [
                    domain : 'component',
                    actions: 'create'
                ]
            ),
        ],

        roles: [
            /**
            * 管理员角色授予所有权限（即超级用户）。
            */
            new MemoryCRole(
                id: Roles.ADMIN_ROLE_ID,
                description: '管理员角色', // Administrator Role
                privileges: [
                    'nx-all'
                ]
            ),

            /**
            * 匿名角色授予未认证用户的权限。
            */
            new MemoryCRole(
                id: Roles.ANONYMOUS_ROLE_ID,
                description: '匿名角色', // Anonymous Role
                privileges: [
                    'nx-search-read',
                    'nx-healthcheck-read',
                    'nx-repository-view-*-*-browse',
                    'nx-repository-view-*-*-read'
                ]
            )
        ]
    )
  }
}

