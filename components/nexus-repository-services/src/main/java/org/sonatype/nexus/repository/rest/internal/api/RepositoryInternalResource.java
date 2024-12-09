package org.sonatype.nexus.repository.rest.internal.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.manager.RepositoryManager;
import org.sonatype.nexus.repository.security.RepositoryPermissionChecker;
import org.sonatype.nexus.repository.security.RepositorySelector;
import org.sonatype.nexus.rest.Resource;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Streams.stream;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.29
 */
@Named
@Singleton
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path(RepositoryInternalResource.RESOURCE_PATH)
public class RepositoryInternalResource
    extends ComponentSupport
    implements Resource
{
  static final String RESOURCE_PATH = "internal/ui/repositories";

  static final RepositoryXO ALL_REFERENCE = new RepositoryXO(
      RepositorySelector.all().toSelector(),
      "(All Repositories)"
  );

  private final List<Format> formats;

  private final RepositoryManager repositoryManager;

  private final RepositoryPermissionChecker repositoryPermissionChecker;

  @Inject
  public RepositoryInternalResource(
      final List<Format> formats,
      final RepositoryManager repositoryManager,
      final RepositoryPermissionChecker repositoryPermissionChecker)
  {
    this.formats = checkNotNull(formats);
    this.repositoryManager = checkNotNull(repositoryManager);
    this.repositoryPermissionChecker = checkNotNull(repositoryPermissionChecker);
  }

  @GET
  public List<RepositoryXO> getRepositories(
      @QueryParam("withAll") final boolean withAll,
      @QueryParam("withFormats") final boolean withFormats)
  {
    List<RepositoryXO> repositories = stream(repositoryManager.browse())
        .filter(repositoryPermissionChecker::userCanBrowseRepository)
        .map(repository -> new RepositoryXO(repository.getName(), repository.getName()))
        .sorted(Comparator.comparing(RepositoryXO::getName))
        .collect(toList());

    List<RepositoryXO> result = new ArrayList<>();
    if (withAll) {
      result.add(ALL_REFERENCE);
    }
    if (withFormats) {
      formats.stream()
          .map(format -> new RepositoryXO(
              RepositorySelector.allOfFormat(format.getValue()).toSelector(),
              "(All " + format.getValue() + " Repositories)"
          ))
          .sorted(Comparator.comparing(RepositoryXO::getName))
          .forEach(result::add);
    }
    result.addAll(repositories);

    return result;
  }
}
