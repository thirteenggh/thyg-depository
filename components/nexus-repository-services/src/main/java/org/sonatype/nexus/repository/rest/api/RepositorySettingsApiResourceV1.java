package org.sonatype.nexus.repository.rest.api;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import static org.sonatype.nexus.repository.rest.api.RepositorySettingsApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class RepositorySettingsApiResourceV1
    extends RepositorySettingsApiResource
{
  public static final String RESOURCE_URI = V1_API_PREFIX + "/repositorySettings";

  @Inject
  public RepositorySettingsApiResourceV1(
      final AuthorizingRepositoryManager authorizingRepositoryManager,
      @Named("default") final ApiRepositoryAdapter defaultAdapter,
      final Map<String, ApiRepositoryAdapter> convertersByFormat)
  {
    super(authorizingRepositoryManager, defaultAdapter, convertersByFormat);
  }
}
