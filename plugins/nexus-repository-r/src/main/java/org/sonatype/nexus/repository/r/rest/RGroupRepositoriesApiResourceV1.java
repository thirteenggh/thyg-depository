package org.sonatype.nexus.repository.r.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1;

/**
 * @since 3.28
 */
@Named
@Singleton
@Path(RepositoriesApiResourceV1.RESOURCE_URI + "/r/group")
public class RGroupRepositoriesApiResourceV1
    extends RGroupRepositoriesApiResource
{
}
