package org.sonatype.nexus.security.user;

import java.util.HashSet;
import java.util.Set;

import org.sonatype.nexus.security.AbstractSecurityTest;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.config.MemorySecurityConfiguration;
import org.sonatype.nexus.security.role.RoleIdentifier;

import org.junit.Assert;
import org.junit.Test;

public class MissingRoleUserManagerTest
    extends AbstractSecurityTest
{
  @Override
  protected MemorySecurityConfiguration initialSecurityConfiguration() {
    return MissingRoleUserManagerTestSecurity.securityModel();
  }

  @Test
  public void testInvalidRoleMapping() throws Exception {
    SecuritySystem userManager = getSecuritySystem();

    User user = userManager.getUser("jcoder");
    Assert.assertNotNull(user);

    Set<String> roleIds = new HashSet<String>();
    for (RoleIdentifier role : user.getRoles()) {
      Assert.assertNotNull("User has null role.", role);
      roleIds.add(role.getRoleId());
    }
    Assert.assertFalse(roleIds.contains("INVALID-ROLE-BLA-BLA"));
  }
}
