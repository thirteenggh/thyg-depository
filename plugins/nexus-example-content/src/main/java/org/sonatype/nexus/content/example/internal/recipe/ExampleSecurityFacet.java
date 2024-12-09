package org.sonatype.nexus.content.example.internal.recipe;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;

/**
 * Example format security facet.
 *
 * @since 3.24
 */
@Named
public class ExampleSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public ExampleSecurityFacet(final ExampleFormatSecurityContributor securityContributor,
                              @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                              final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityContributor, variableResolverAdapter, contentPermissionChecker);
  }
}
