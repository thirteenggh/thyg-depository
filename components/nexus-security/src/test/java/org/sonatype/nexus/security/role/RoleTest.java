package org.sonatype.nexus.security.role;

import org.sonatype.goodies.testsupport.TestSupport;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link Role}.
 */
public class RoleTest
    extends TestSupport
{
  @Test
  public void testCompareDifferentId() throws Exception {
    Role roleA = new Role();
    roleA.setName("ID1");
    roleA.setRoleId("ID1");
    roleA.setSource("source");

    Role roleB = new Role();
    roleB.setName("ID2");
    roleB.setRoleId("ID2");
    roleB.setSource("source");

    Assert.assertEquals(-1, roleA.compareTo(roleB));
    Assert.assertEquals(1, roleB.compareTo(roleA));
  }

  @Test
  public void testCompareDifferentSource() throws Exception {
    Role roleA = new Role();
    roleA.setName("ID1");
    roleA.setRoleId("ID1");
    roleA.setSource("source1");

    Role roleB = new Role();
    roleB.setName("ID1");
    roleB.setRoleId("ID1");
    roleB.setSource("source2");

    Assert.assertEquals(-1, roleA.compareTo(roleB));
    Assert.assertEquals(1, roleB.compareTo(roleA));
  }
}
