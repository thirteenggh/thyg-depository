package org.sonatype.nexus.security.privilege;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.PermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Tests for {@link ApplicationPermission}.
 */
public class ApplicationPermissionTest
    extends TestSupport
{
  private final PermissionResolver permissionResolver = new WildcardPermissionResolver();

  @Test
  public void simpleApplicationPermissionsMatch() {

    Permission authorizedPermission = new ApplicationPermission("feature", "action", "anotherAction");

    // simulate @RequiresPermissions resolution on UI resources
    Permission requiredPermission = permissionResolver.resolvePermission("nexus:feature:action");

    assertThat(authorizedPermission.implies(requiredPermission), is(true));
  }

  @Test
  public void complexApplicationPermissionsMatch() {

    Permission authorizedPermission = new ApplicationPermission("feature:method", "action", "anotherAction");

    // simulate @RequiresPermissions resolution on UI resources
    Permission requiredPermission = permissionResolver.resolvePermission("nexus:feature:method:action");

    assertThat(authorizedPermission.implies(requiredPermission), is(true));
  }
}
