package org.sonatype.nexus.repository.cocoapods.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Cocoapods format security facet
 *
 * @since 3.19
 */
@Named
public class CocoapodsSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public CocoapodsSecurityFacet(final CocoapodsFormatSecurityContributor securityResource,
                                @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                                final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
