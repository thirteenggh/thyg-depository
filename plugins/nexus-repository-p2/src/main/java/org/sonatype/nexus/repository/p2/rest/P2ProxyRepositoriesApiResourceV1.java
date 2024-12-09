package org.sonatype.nexus.repository.p2.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1;

/**
 * @since 1.1.7
 */
@Named
@Singleton
@Path(RepositoriesApiResourceV1.RESOURCE_URI + "/p2/proxy")
public class P2ProxyRepositoriesApiResourceV1 extends P2ProxyRepositoriesApiResource
{
}
