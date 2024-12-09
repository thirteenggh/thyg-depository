package org.sonatype.repository.conan.internal.security;

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

public class ConanSecurityFacetTest
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
  ConanFormatSecurityContributor securityContributor;

  ConanSecurityFacet conanSecurityFacet;

  @Before
  public void setupConfig() throws Exception {
    when(request.getPath()).thenReturn("/some/path.txt");
    when(request.getAction()).thenReturn(HttpMethods.GET);

    when(repository.getFormat()).thenReturn(new Format("conan") { });
    when(repository.getName()).thenReturn("ConanSecurityFacetTest");

    conanSecurityFacet = new ConanSecurityFacet(securityContributor,
        variableResolverAdapter, contentPermissionChecker);

    conanSecurityFacet.attach(repository);
  }

  @SuppressWarnings("java:S2699") //Sonar wants assertions, but none seem worthwhile here
  @Test
  public void testEnsurePermitted_permitted() {
    when(contentPermissionChecker.isPermitted(eq("ConanSecurityFacetTest"), eq("conan"), eq(READ), any()))
        .thenReturn(true);
    conanSecurityFacet.ensurePermitted(request);
  }

  @Test
  public void testEnsurePermitted_notPermitted() {
    when(contentPermissionChecker.isPermitted(eq("ConanSecurityFacetTest"), eq("conan"), eq(READ), any()))
        .thenReturn(false);
    try {
      conanSecurityFacet.ensurePermitted(request);
      fail("AuthorizationException should have been thrown");
    }
    catch (AuthorizationException e) {
      //expected
    }

    verify(contentPermissionChecker).isPermitted(eq("ConanSecurityFacetTest"), eq("conan"), eq(READ), any());
  }
}
