package org.sonatype.nexus.repository.p2.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceBeta;

import io.swagger.annotations.Api;

/**
 * @since 1.1.7
 * @deprecated - prefer to use {@link P2ProxyRepositoriesApiResourceV1 } instead of Beta.
 */
@Named
@Singleton
@Path(RepositoriesApiResourceBeta.RESOURCE_URI + "/p2/proxy")
@Api(hidden = true)
@Deprecated
public class P2ProxyRepositoriesApiResourceBeta
    extends P2ProxyRepositoriesApiResource
{
}
