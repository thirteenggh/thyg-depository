package org.sonatype.nexus.repository.conda.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Conda format security facet.
 *
 * @since 3.19
 */
@Named
public class CondaSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public CondaSecurityFacet(final CondaFormatSecurityContributor securityResource,
                            @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                            final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
