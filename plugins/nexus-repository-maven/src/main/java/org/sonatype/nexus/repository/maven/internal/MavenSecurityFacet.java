package org.sonatype.nexus.repository.maven.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Maven 2 security facet.
 *
 * @since 3.0
 */
@Named
public class MavenSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public MavenSecurityFacet(final MavenFormatSecurityContributor securityContributor,
                            @Named(Maven2Format.NAME) final VariableResolverAdapter variableResolverAdapter,
                            final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityContributor, variableResolverAdapter, contentPermissionChecker);
  }
}
