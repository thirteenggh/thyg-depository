package org.sonatype.nexus.repository.npm.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.security.ContentPermissionChecker;
import org.sonatype.nexus.repository.security.SecurityFacetSupport;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.security.BreadActions;

/**
 * npm format security facet.
 *
 * @since 3.0
 */
@Named
public class NpmSecurityFacet
    extends SecurityFacetSupport
{
  @Inject
  public NpmSecurityFacet(final NpmFormatSecurityContributor securityContributor,
                          @Named("simple") final VariableResolverAdapter variableResolverAdapter,
                          final ContentPermissionChecker contentPermissionChecker)
  {
    super(securityContributor, variableResolverAdapter, contentPermissionChecker);
  }

  /**
   * Returns BREAD action for request action.
   */
  @Override
  protected String action(final Request request) {
    if (request.getPath().startsWith(NpmPaths.NPM_V1_SECURITY_AUDITS)) {
      return BreadActions.READ;
    }
    return super.action(request);
  }
}
