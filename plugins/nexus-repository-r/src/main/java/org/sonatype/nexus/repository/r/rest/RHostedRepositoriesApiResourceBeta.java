package org.sonatype.nexus.repository.r.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceBeta;

import io.swagger.annotations.Api;

/**
 * @since 3.28
 * @deprecated - prefer to use {@link RHostedRepositoriesApiResourceV1 } instead of Beta.
 */
@Named
@Singleton
@Path(RepositoriesApiResourceBeta.RESOURCE_URI + "/r/hosted")
@Api(hidden = true)
@Deprecated
public class RHostedRepositoriesApiResourceBeta
    extends RHostedRepositoriesApiResource
{
}
