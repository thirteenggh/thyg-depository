package org.sonatype.nexus.security.internal

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.security.SecurityApi
import org.sonatype.nexus.security.SecuritySystem
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration
import org.sonatype.nexus.security.anonymous.AnonymousManager
import org.sonatype.nexus.security.role.Role
import org.sonatype.nexus.security.role.RoleIdentifier
import org.sonatype.nexus.security.user.User
import org.sonatype.nexus.security.user.UserStatus

import com.google.common.collect.Sets

import static com.google.common.base.Preconditions.checkNotNull
import static org.sonatype.nexus.security.user.UserManager.DEFAULT_SOURCE

/**
 * @since 3.0
 */
@Named
@Singleton
class SecurityApiImpl
    extends ComponentSupport
    implements SecurityApi
{
  @Inject
  AnonymousManager anonymousManager

  @Inject
  SecuritySystem securitySystem

  @Override
  AnonymousConfiguration setAnonymousAccess(final boolean enabled) {
    AnonymousConfiguration anonymousConfiguration = anonymousManager.configuration

    if (!anonymousManager.configured || anonymousConfiguration.enabled != enabled) {
      anonymousConfiguration.enabled = enabled
      anonymousManager.configuration = anonymousConfiguration
      log.info('Anonymous access configuration updated to: {}', anonymousConfiguration)
    }
    else {
      log.info('Anonymous access configuration unchanged at: {}', anonymousConfiguration)
    }
    return anonymousConfiguration
  }

  @Override
  User addUser(final String id, final String firstName, final String lastName, final String email, final boolean active,
               final String password, final List<String> roleIds)
  {
    return securitySystem.addUser(
        new User(
            userId: checkNotNull(id),
            source: DEFAULT_SOURCE,
            firstName: checkNotNull(firstName),
            lastName: checkNotNull(lastName),
            emailAddress: checkNotNull(email),
            status: checkNotNull(active) ? UserStatus.active : UserStatus.disabled,
            roles: checkNotNull(roleIds) ? roleIds.collect { new RoleIdentifier(DEFAULT_SOURCE, it) } : []
        ), password
    )
  }

  @Override
  Role addRole(final String id, final String name, final String description, final List<String> privileges,
               final List<String> roles)
  {
    return securitySystem.getAuthorizationManager(DEFAULT_SOURCE).addRole(
        new Role(
            roleId: checkNotNull(id),
            source: DEFAULT_SOURCE,
            name: checkNotNull(name),
            description: description,
            privileges: Sets.newHashSet(checkNotNull(privileges)),
            roles: Sets.newHashSet(checkNotNull(roles))
        )
    )
  }

  @Override
  User setUserRoles(final String userId, final List<String> roleIds) {
    User user = securitySystem.getUser(userId, DEFAULT_SOURCE)
    user.roles = roleIds.collect{ new RoleIdentifier(DEFAULT_SOURCE, it)}
    securitySystem.updateUser(user)
  }
}
