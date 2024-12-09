package org.sonatype.nexus.repository.p2.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * P2 format security facet.
 */
@Named
public class P2SecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public P2SecurityFacet(final P2FormatSecurityContributor securityResource,
                        @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                        final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
