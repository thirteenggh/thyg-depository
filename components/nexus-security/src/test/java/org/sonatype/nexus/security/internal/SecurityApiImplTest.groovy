package org.sonatype.nexus.security.internal

import org.sonatype.nexus.security.SecurityApi
import org.sonatype.nexus.security.SecuritySystem
import org.sonatype.nexus.security.TestAnonymousConfiguration
import org.sonatype.nexus.security.anonymous.AnonymousManager
import org.sonatype.nexus.security.authz.AuthorizationManager
import org.sonatype.nexus.security.role.Role
import org.sonatype.nexus.security.user.User
import org.sonatype.nexus.security.user.UserManager
import org.sonatype.nexus.security.user.UserStatus

import spock.lang.Specification

/**
 * @since 3.0
 */
class SecurityApiImplTest
    extends Specification
{
  AnonymousManager anonymousManager = Mock()

  SecuritySystem securitySystem = Mock()

  AuthorizationManager authorizationManager = Mock()

  SecurityApi api = new SecurityApiImpl(anonymousManager: anonymousManager, securitySystem: securitySystem)

  def 'Can update anonymous settings'() {
    given:
      def configuration = new TestAnonymousConfiguration(enabled: true)

    when:
      def updatedConfiguration = api.setAnonymousAccess(false)

    then:
      1 * anonymousManager.getConfiguration() >> configuration
      1 * anonymousManager.setConfiguration(_)
      !updatedConfiguration.enabled
  }

  def 'No save is made when configured and anonymous settings already match'() {
    given:
      def configuration = new TestAnonymousConfiguration(enabled: true)
      anonymousManager.isConfigured() >> true

    when:
      def updatedConfiguration = api.setAnonymousAccess(true)

    then:
      1 * anonymousManager.getConfiguration() >> configuration
      0 * anonymousManager.setConfiguration(_)
      updatedConfiguration.enabled
  }

  def 'One save is made when unconfigured and anonymous settings already match'() {
    given:
      def configuration = new TestAnonymousConfiguration(enabled: false)
      anonymousManager.isConfigured() >> false

    when:
      def updatedConfiguration = api.setAnonymousAccess(false)

    then:
      1 * anonymousManager.getConfiguration() >> configuration
      1 * anonymousManager.setConfiguration(_)
      !updatedConfiguration.enabled
  }

  def 'Can add a new User'() {
    User user

    when:
      api.addUser('foo', 'bar', 'baz', 'foo@bar.com', true, 'pass', ['roleId'])

    then:
      // argument capture for the method call on the stub
      1 * securitySystem.addUser(_, 'pass') >> { args -> user = args[0] }
      with(user) {
        userId == 'foo'
        source == UserManager.DEFAULT_SOURCE
        firstName == 'bar'
        lastName == 'baz'
        emailAddress == 'foo@bar.com'
        status == UserStatus.active
        getRoles().size() == 1
        getRoles()[0].source == UserManager.DEFAULT_SOURCE
        getRoles()[0].roleId == 'roleId'
      }
  }

  def 'Can add a new Role'() {
    Role role

    when:
      api.addRole('foo', 'bar', 'baz', ['priv'], ['role'])

    then:
      1 * securitySystem.getAuthorizationManager(_) >> authorizationManager
      1 * authorizationManager.addRole(_) >> { args -> role = args[0] }
      with(role) {
        roleId == 'foo'
        source == UserManager.DEFAULT_SOURCE
        name == 'bar'
        description == 'baz'
        privileges == ['priv'] as Set
        roles == ['role'] as Set
      }
  }
}
