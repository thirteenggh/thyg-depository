package org.sonatype.nexus.repository.rest.api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;
import org.sonatype.nexus.rest.Resource;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.26
 */
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class RepositorySettingsApiResource
    extends ComponentSupport
    implements Resource, RepositorySettingsApiResourceDoc
{
  private final AuthorizingRepositoryManager authorizingRepositoryManager;

  private final Map<String, ApiRepositoryAdapter> convertersByFormat;

  private final ApiRepositoryAdapter defaultAdapter;

  @Inject
  public RepositorySettingsApiResource(
      final AuthorizingRepositoryManager authorizingRepositoryManager,
      @Named("default") final ApiRepositoryAdapter defaultAdapter,
      final Map<String, ApiRepositoryAdapter> convertersByFormat)
  {
    this.authorizingRepositoryManager = checkNotNull(authorizingRepositoryManager);
    this.defaultAdapter = checkNotNull(defaultAdapter);
    this.convertersByFormat = checkNotNull(convertersByFormat);
  }

  @Override
  @RequiresAuthentication
  @GET
  public List<AbstractApiRepository> getRepositories() {
    return authorizingRepositoryManager.getRepositoriesWithAdmin().stream().map(this::convert)
        .collect(Collectors.toList());
  }

  private AbstractApiRepository convert(final Repository repository) {
    return convertersByFormat.getOrDefault(repository.getFormat().toString(), defaultAdapter).adapt(repository);
  }
}
