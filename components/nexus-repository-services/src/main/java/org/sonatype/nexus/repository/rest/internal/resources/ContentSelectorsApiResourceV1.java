package org.sonatype.nexus.repository.rest.internal.resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.internal.rest.SecurityApiResourceV1;
import org.sonatype.nexus.selector.SelectorFactory;
import org.sonatype.nexus.selector.SelectorManager;

import static org.sonatype.nexus.repository.rest.internal.resources.ContentSelectorsApiResourceV1.RESOURCE_URI;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class ContentSelectorsApiResourceV1
    extends ContentSelectorsApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "content-selectors";

  @Inject
  public ContentSelectorsApiResourceV1(
      final SelectorFactory selectorFactory,
      final SelectorManager selectorManager)
  {
    super(selectorFactory, selectorManager);
  }
}
