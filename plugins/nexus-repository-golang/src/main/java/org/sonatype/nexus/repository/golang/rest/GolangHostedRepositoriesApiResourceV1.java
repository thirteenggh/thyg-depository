package org.sonatype.nexus.repository.golang.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceV1;

import static org.sonatype.nexus.common.app.FeatureFlags.FEATURE_GOLANG_HOSTED;
import static org.sonatype.nexus.repository.golang.rest.GolangHostedRepositoriesApiResourceV1.RESOURCE_URI;

/**
 * @since 3.26
 */
@FeatureFlag(name = FEATURE_GOLANG_HOSTED)
@Named
@Singleton
@Path(RESOURCE_URI)
public class GolangHostedRepositoriesApiResourceV1
    extends GolangHostedRepositoriesApiResource
{
  static final String RESOURCE_URI = RepositoriesApiResourceV1.RESOURCE_URI + "/go/hosted";
}
