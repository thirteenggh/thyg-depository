package org.sonatype.nexus.repository.view;

import javax.annotation.Nullable;
import javax.inject.Named;

import org.sonatype.nexus.repository.FacetSupport;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Configurable {@link ViewFacet} implementation.
 *
 * @since 3.0
 */
@Named
public class ConfigurableViewFacet
    extends FacetSupport
    implements ViewFacet
{
  private Router router;

  public void configure(final Router router) {
    checkNotNull(router);
    checkState(this.router == null, "Router already configured");
    this.router = router;
  }

  @Override
  public Response dispatch(final Request request) throws Exception {
    return dispatch(request, null);
  }

  /**
   * @since 3.1
   */
  @Override
  public Response dispatch(final Request request, @Nullable final Context context) throws Exception {
    checkState(router != null, "Router not configured");
    return router.dispatch(getRepository(), request, context);
  }
}
