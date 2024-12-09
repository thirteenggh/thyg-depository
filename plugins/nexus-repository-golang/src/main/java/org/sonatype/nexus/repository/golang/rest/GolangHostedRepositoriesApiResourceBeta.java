package org.sonatype.nexus.repository.golang.rest;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.repository.rest.api.RepositoriesApiResourceBeta;
import org.sonatype.nexus.rest.APIConstants;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.common.app.FeatureFlags.FEATURE_GOLANG_HOSTED;
import static org.sonatype.nexus.repository.golang.rest.GolangHostedRepositoriesApiResourceBeta.RESOURCE_URI;

/**
 * @since 3.26
 * @deprecated the 'beta' prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX}
 * instead. Support backward compatibility.
 */
@FeatureFlag(name = FEATURE_GOLANG_HOSTED)
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class GolangHostedRepositoriesApiResourceBeta
    extends GolangHostedRepositoriesApiResource
{
  static final String RESOURCE_URI = RepositoriesApiResourceBeta.RESOURCE_URI + "/go/hosted";
}
