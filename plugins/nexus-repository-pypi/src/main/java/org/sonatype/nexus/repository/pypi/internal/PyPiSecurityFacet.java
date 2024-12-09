package org.sonatype.nexus.repository.pypi.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.security.BreadActions;

import static org.sonatype.nexus.repository.pypi.internal.PyPiPathUtils.isSearchRequest;

/**
 * PyPI format security facet.
 *
 * @since 3.1
 */
@Named
public class PyPiSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public PyPiSecurityFacet(final PyPiFormatSecurityContributor securityContributor,
                           @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                           final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityContributor, variableResolverAdapter, contentPermissionChecker);
  }

  protected String action(final Request request) {
    if (isSearchRequest(request)) {
      return BreadActions.READ;
    }
    return super.action(request);
  }
}
