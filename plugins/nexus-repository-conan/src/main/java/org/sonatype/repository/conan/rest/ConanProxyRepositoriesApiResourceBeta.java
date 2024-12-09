package org.sonatype.repository.conan.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceBeta;

import io.swagger.annotations.Api;

/**
 * @since 3.28
 * @deprecated - prefer to use {@link ConanProxyRepositoriesApiResourceV1 } instead of Beta.
 */
@Named
@Singleton
@Path(RepositoriesApiResourceBeta.RESOURCE_URI + "/conan/proxy")
@Deprecated
@Api(hidden = true)
public class ConanProxyRepositoriesApiResourceBeta
    extends ConanProxyRepositoriesApiResource
{
}
