package org.sonatype.repository.helm.internal.security;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpMethods;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.view.Request;

import org.apache.shiro.authz.AuthorizationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sonatype.nexus.security.BreadActions.READ;

public class HelmSecurityFacetTest
    extends TestSupport
{
  @Mock
  Request request;

  @Mock
  Repository repository;

  @Mock
  ContentPermissionChecker contentPermissionChecker;

  @Mock
  VariableResolverAdapter variableResolverAdapter;

  @Mock
  HelmFormatSecurityContributor securityContributor;

  HelmSecurityFacet helmSecurityFacet;

  @Before
  public void setupConfig() throws Exception {
    when(request.getPath()).thenReturn("/some/path.txt");
    when(request.getAction()).thenReturn(HttpMethods.GET);

    when(repository.getFormat()).thenReturn(new Format("helm") { });
    when(repository.getName()).thenReturn("HelmSecurityFacetTest");

    helmSecurityFacet = new HelmSecurityFacet(securityContributor,
        variableResolverAdapter, contentPermissionChecker);

    helmSecurityFacet.attach(repository);
  }

  @SuppressWarnings("java:S2699") //Sonar wants assertions, but none seem worthwhile here
  @Test
  public void testEnsurePermitted_permitted() throws Exception {
    when(contentPermissionChecker.isPermitted(eq("HelmSecurityFacetTest"), eq("helm"), eq(READ), any()))
        .thenReturn(true);
    helmSecurityFacet.ensurePermitted(request);
  }

  @Test
  public void testEnsurePermitted_notPermitted() throws Exception {
    when(contentPermissionChecker.isPermitted(eq("HelmSecurityFacetTest"), eq("helm"), eq(READ), any()))
        .thenReturn(false);
    try {
      helmSecurityFacet.ensurePermitted(request);
      fail("AuthorizationException should have been thrown");
    }
    catch (AuthorizationException e) {
      //expected
    }

    verify(contentPermissionChecker).isPermitted(eq("HelmSecurityFacetTest"), eq("helm"), eq(READ), any());
  }
}
