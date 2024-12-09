package org.sonatype.nexus.internal.scheduling

import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.security.config.MemorySecurityConfiguration
import org.sonatype.nexus.security.config.SecurityContributor
import org.sonatype.nexus.security.config.memory.MemoryCPrivilege

/**
 * Scheduling security configuration.
 *
 * @since 3.0
 */
@Named
@Singleton
class SchedulingSecurityContributor
    implements SecurityContributor
{
  @Override
  MemorySecurityConfiguration getContribution() {
    return new MemorySecurityConfiguration(
        privileges: [
            new MemoryCPrivilege(
                id: 'nx-tasks-all',
                description: '调度任务的所有权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: '*'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-tasks-create',
                description: '调度任务的创建权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: 'create,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-tasks-read',
                description: '调度任务的读取权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: 'read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-tasks-update',
                description: '调度任务的更新权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: 'update,read'
                ]
            ),
            new MemoryCPrivilege(
                id: 'nx-tasks-delete',
                description: '调度任务的删除权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: 'delete,read'
                ]
            ),

            new MemoryCPrivilege(
                id: 'nx-tasks-run',
                description: '调度任务的运行权限',
                type: 'application',
                properties: [
                    domain : 'tasks',
                    actions: 'start,stop'
                ]
            )
        ]
    )
  }
}

