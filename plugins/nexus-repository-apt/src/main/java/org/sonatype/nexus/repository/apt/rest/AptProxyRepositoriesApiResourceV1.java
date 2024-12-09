package org.sonatype.nexus.repository.apt.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1;

import static org.sonatype.nexus.repository.apt.rest.AptProxyRepositoriesApiResourceV1.RESOURCE_URI;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class AptProxyRepositoriesApiResourceV1
    extends AptProxyRepositoriesApiResource
{
  static final String RESOURCE_URI = RepositoriesApiResourceV1.RESOURCE_URI + "/apt/proxy";
}
