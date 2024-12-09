package org.sonatype.nexus.repository.raw.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * RAW security facet.
 *
 * @since 3.0
 */
@Named
public class RawSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public RawSecurityFacet(final RawFormatSecurityContributor securityContributor,
                          @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                          final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityContributor, variableResolverAdapter, contentPermissionChecker);
  }
}
