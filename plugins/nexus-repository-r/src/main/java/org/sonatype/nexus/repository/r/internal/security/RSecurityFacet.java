package org.sonatype.nexus.repository.r.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * R format security facet.
 *
 * @since 3.28
 */
@Named
public class RSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public RSecurityFacet(final RFormatSecurityContributor securityResource,
                        @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                        final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
