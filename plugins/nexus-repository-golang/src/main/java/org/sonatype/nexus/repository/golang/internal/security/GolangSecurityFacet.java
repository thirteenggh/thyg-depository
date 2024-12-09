package org.sonatype.nexus.repository.golang.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Go format security facet.
 *
 * @since 3.17
 */
@Named
public class GolangSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public GolangSecurityFacet(final GolangFormatSecurityContributor securityResource,
                             @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                             final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
