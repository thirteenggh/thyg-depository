package org.sonatype.nexus.repository.rest.internal.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceBeta;
import org.sonatype.nexus.selector.SelectorFactory;
import org.sonatype.nexus.selector.SelectorManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.repository.rest.internal.resources.ContentSelectorsApiResourceBeta.RESOURCE_URI;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class ContentSelectorsApiResourceBeta
    extends ContentSelectorsApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "content-selectors";

  @Inject
  public ContentSelectorsApiResourceBeta(
      final SelectorFactory selectorFactory,
      final SelectorManager selectorManager)
  {
    super(selectorFactory, selectorManager);
  }
}
