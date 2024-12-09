package org.sonatype.nexus.script.plugin.internal.security

import javax.inject.Named
import javax.inject.Singleton
import org.sonatype.nexus.security.config.CPrivilege
import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

import groovy.transform.CompileStatic

/**
 * Script security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
@CompileStatic
class ScriptSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    def configuration = new MemorySecurityConfiguration()
    configuration.setPrivileges([
        new MemoryCPrivilege(
            id: 'nx-script-*-*',
            description: '脚本的所有权限',
            type: 'script',
            properties: [
                name : '*',
                actions: '*'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-browse',
            description: '脚本的浏览权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'browse,read'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-add',
            description: '脚本的添加权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'add,read'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-edit',
            description: '脚本的编辑权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'edit,read'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-read',
            description: '脚本的读取权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'read'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-delete',
            description: '脚本的删除权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'delete,read'
            ]
        ),
        new MemoryCPrivilege(
            id: 'nx-script-*-run',
            description: '脚本的运行权限',
            type: 'script',
            properties: [
                name : '*',
                actions: 'run'
            ]
        )
    ] as List<CPrivilege>)
    return configuration
  }
}
