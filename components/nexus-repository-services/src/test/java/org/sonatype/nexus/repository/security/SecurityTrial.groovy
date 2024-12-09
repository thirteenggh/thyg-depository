package org.sonatype.nexus.repository.security

import org.sonatype.goodies.testsupport.TestSupport

import org.apache.shiro.authz.permission.DomainPermission
import org.apache.shiro.authz.permission.WildcardPermission
import org.junit.Test

/**
 * Security trials.
 */
class SecurityTrial
  extends TestSupport
{
  @Test
  void 'wildcard permission string'() {
    def p = new WildcardPermission('foo:bar:*:baz')
    log p
  }

  @Test
  void 'domain permission string'() {
    def p = new DomainPermission('foo,bar', 'read,wrote')
    log p
  }

  private static class CustomPermission
    extends DomainPermission
  {
    CustomPermission(final String actions, final String targets) {
      super(actions, targets)
    }
  }

  @Test
  void 'custom domain permission string'() {
    def p = new CustomPermission('foo,bar', 'read,wrote')
    log p
  }

  @Test
  void 'implied permission'() {
    def granted = new WildcardPermission('test:*')
    def permission = new WildcardPermission('test:foo')
    log granted.implies(permission)
  }
}
