package org.sonatype.repository.conan.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Conan format security facet.
 *
 * @since 3.28
 */
@Named
public class ConanSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public ConanSecurityFacet(final ConanFormatSecurityContributor securityResource,
                            @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                            final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
