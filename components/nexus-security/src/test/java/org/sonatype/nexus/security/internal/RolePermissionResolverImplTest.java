package org.sonatype.nexus.security.internal;

import java.util.Collections;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.security.authz.AuthorizationConfigurationChanged;
import org.sonatype.nexus.security.config.SecurityConfigurationManager;
import org.sonatype.nexus.security.role.NoSuchRoleException;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RolePermissionResolverImpl}.
 */
public class RolePermissionResolverImplTest
    extends TestSupport
{
  private RolePermissionResolverImpl underTest;

  private SecurityConfigurationManager securityConfigurationManager;

  @Before
  public void setUp() throws Exception {
    securityConfigurationManager = mock(SecurityConfigurationManager.class);
    when(securityConfigurationManager.readRole(any())).thenThrow(new NoSuchRoleException("Role not found"));
    underTest = new RolePermissionResolverImpl(securityConfigurationManager, Collections.emptyList(),
        mock(EventManager.class), 10);
  }

  @Test
  public void resolvePermissionsInRole_roleNotFoundCache() throws Exception {
    underTest.resolvePermissionsInRole("role1");
    verify(securityConfigurationManager).readRole(any());

    //just call it 3 more times for fun
    underTest.resolvePermissionsInRole("role1");
    underTest.resolvePermissionsInRole("role1");
    underTest.resolvePermissionsInRole("role1");

    //should still have only been called once
    verify(securityConfigurationManager).readRole(any());

    //simulate event being fired, which clears cache
    underTest.on(new AuthorizationConfigurationChanged());

    underTest.resolvePermissionsInRole("role1");
    verify(securityConfigurationManager, times(2)).readRole(any());

    //and finally make sure we are hitting cache again
    underTest.resolvePermissionsInRole("role1");
    underTest.resolvePermissionsInRole("role1");
    underTest.resolvePermissionsInRole("role1");
    verify(securityConfigurationManager, times(2)).readRole(any());
  }
}
