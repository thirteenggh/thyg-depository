package org.sonatype.nexus.repository.rest.api;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.repository.rest.api.RepositorySettingsApiResourceBeta.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

/**
 * @since 3.26
 * @deprecated the 'beta' prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX}
 * instead. Support backward compatibility.
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class RepositorySettingsApiResourceBeta
    extends RepositorySettingsApiResource
{
  public static final String RESOURCE_URI = BETA_API_PREFIX + "/repositories";

  @Inject
  public RepositorySettingsApiResourceBeta(
      final AuthorizingRepositoryManager authorizingRepositoryManager,
      @Named("default") final ApiRepositoryAdapter defaultAdapter,
      final Map<String, ApiRepositoryAdapter> convertersByFormat)
  {
    super(authorizingRepositoryManager, defaultAdapter, convertersByFormat);
  }
}
