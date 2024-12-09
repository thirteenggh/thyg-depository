package org.sonatype.nexus.security.config

import org.sonatype.nexus.security.config.memory.MemoryCUserRoleMapping

import spock.lang.Specification
import spock.lang.Unroll

class MemorySecurityConfigurationTest
    extends Specification
{
  MemorySecurityConfiguration config

  def setup() {
    config = new MemorySecurityConfiguration()
  }

  @Unroll
  def 'userRoleMappings for source: \'#src\' read ignores case: #ignoreCase'(src, ignoreCase, isFound) {
    given: 'an existing user role mapping'
      def roles = ['test-role'] as Set
      def newUserRoleMapping = new MemoryCUserRoleMapping(userId: 'userid', source: src, roles: roles)
      config.addUserRoleMapping(newUserRoleMapping)

    when: 'a users roles are retrieved with different user id casing'
      def roleMapping = config.getUserRoleMapping('USERID', src)

    then: 'the mapping is found if source is case insensitive'
      roleMapping != null == isFound
      if (isFound) {
        roleMapping.roles == roles
      }

    where:
      src << ['default', 'ldap', 'crowd', 'other']
      ignoreCase << ['true', 'true', 'true', 'false']
      isFound << [true, true, true, false]
  }
}
