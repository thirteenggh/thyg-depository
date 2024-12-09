package org.sonatype.nexus.security.role;

import java.util.Collection;
import java.util.Collections;

import javax.validation.ConstraintValidatorContext;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.security.Roles;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.authz.AuthorizationManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RoleNotContainSelfValidatorTest}.
 */
public class RoleNotContainSelfValidatorTest
    extends TestSupport
{
  @Mock
  SecuritySystem securitySystem;

  @Mock
  AuthorizationManager authorizationManager;

  @Mock
  RoleNotContainSelf roleNotContainSelf;

  @Mock
  ConstraintValidatorContext context;

  RoleNotContainSelfValidator underTest;

  @Before
  public void setup() throws Exception {
    when(securitySystem.getAuthorizationManager(any())).thenReturn(authorizationManager);
    when(roleNotContainSelf.id()).thenReturn("getId");
    when(roleNotContainSelf.roleIds()).thenReturn("getRoleIds");
    when(context.buildConstraintViolationWithTemplate(any()))
        .thenReturn(mock(ConstraintValidatorContext.ConstraintViolationBuilder.class));
    underTest = new RoleNotContainSelfValidator(securitySystem);

    underTest.initialize(roleNotContainSelf);
  }

  @Test
  public void testIsValid_noId() {
    assertThat(underTest.isValid(new TestObj(), context), is(true));
  }

  @Test
  public void testIsValid_valid() throws Exception {
    Role adminRole = createRole(Roles.ADMIN_ROLE_ID);
    Role parentRole = createRole("parentRole", "childRole");
    Role childRole = createRole("childRole", "grandchildRole");
    Role grandchildRole = createRole("grandchildRole", Roles.ADMIN_ROLE_ID);

    when(authorizationManager.getRole(Roles.ADMIN_ROLE_ID)).thenReturn(adminRole);
    when(authorizationManager.getRole("parentRole")).thenReturn(parentRole);
    when(authorizationManager.getRole("childRole")).thenReturn(childRole);
    when(authorizationManager.getRole("grandchildRole")).thenReturn(grandchildRole);

    assertThat(underTest.isValid(new TestObj("parentRole", Collections.singleton("childRole")), context), is(true));
  }

  @Test
  public void testIsValid_invalid() throws Exception {
    Role adminRole = createRole(Roles.ADMIN_ROLE_ID);
    Role parentRole = createRole("parentRole", "childRole");
    Role childRole = createRole("childRole", "grandchildRole");
    Role grandchildRole = createRole("grandchildRole", "parentRole", Roles.ADMIN_ROLE_ID);

    when(authorizationManager.getRole(Roles.ADMIN_ROLE_ID)).thenReturn(adminRole);
    when(authorizationManager.getRole("parentRole")).thenReturn(parentRole);
    when(authorizationManager.getRole("childRole")).thenReturn(childRole);
    when(authorizationManager.getRole("grandchildRole")).thenReturn(grandchildRole);

    assertThat(underTest.isValid(new TestObj("parentRole", Collections.singleton("childRole")), context), is(false));
  }

  private Role createRole(String id, String... childRoles) {
    Role role = new Role();
    role.setRoleId(id);
    for (String childRole : childRoles) {
      role.addRole(childRole);
    }

    return role;
  }

  private static class TestObj {
    private String id;
    private Collection<String> roleIds;

    public TestObj() {

    }

    public TestObj(String id, Collection<String> roleIds) {
      this.id = id;
      this.roleIds = roleIds;
    }

    public String getId() {
      return id;
    }

    public Collection<String> getRoleIds() {
      return roleIds;
    }
  }
}
