package org.sonatype.repository.helm.internal.security;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Helm format security facet.
 */
@Named
public class HelmSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public HelmSecurityFacet(
      final HelmFormatSecurityContributor securityResource,
      @Named("simple") final VariableResolverAdapter variableResolverAdapter,
      final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityResource, variableResolverAdapter, contentPermissionChecker);
  }

  @Override
  protected void doValidate(final Configuration configuration) throws Exception {
    super.doValidate(configuration);
  }
}
