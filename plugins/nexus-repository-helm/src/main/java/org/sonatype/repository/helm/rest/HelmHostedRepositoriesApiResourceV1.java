package org.sonatype.repository.helm.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1;

@Named
@Singleton
@Path(RepositoriesApiResourceV1.RESOURCE_URI + "/helm/hosted")
public class HelmHostedRepositoriesApiResourceV1
    extends HelmHostedRepositoriesApiResource
{

}
