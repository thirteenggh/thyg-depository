package org.sonatype.repository.helm.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceBeta;

import io.swagger.annotations.Api;

/**
 * @since 3.28
 */
@Named
@Singleton
@Path(RepositoriesApiResourceBeta.RESOURCE_URI + "/helm/hosted")
@Api(hidden = true)
@Deprecated
public class HelmHostedRepositoriesApiResourceBeta
    extends HelmHostedRepositoriesApiResource
{

}
