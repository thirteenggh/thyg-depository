package org.sonatype.nexus.repository.apt.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * @since 3.17
 */
@Named
public class AptSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public AptSecurityFacet(
                          final AptFormatSecurityContributor securityResource,
                          @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                          final ContentPermissionChecker contentPermissionChecker) {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }
}
