package org.sonatype.nexus.security.authz

import org.sonatype.goodies.testsupport.TestSupport

import org.apache.shiro.authz.permission.WildcardPermission
import org.junit.Test

/**
 * Permission trials.
 */
class PermissionTrial
    extends TestSupport
{
  @Test
  void 'test implied wildcard'() {
    def perm = new WildcardPermission('nexus:something:special:read')
    def granted = new WildcardPermission('nexus:*')
    assert granted.implies(perm)
  }

  @Test
  void 'test implied wildcard without *'() {
    def perm = new WildcardPermission('nexus:something:special:read')
    def granted = new WildcardPermission('nexus')
    assert granted.implies(perm)
  }
}
