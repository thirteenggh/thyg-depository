package org.sonatype.nexus.repository.rest.internal.resources.doc;

import java.util.List;

import org.sonatype.nexus.repository.rest.api.RepositoryXO;
import org.sonatype.nexus.repository.rest.internal.resources.RepositoriesResource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Swagger documentation for {@link RepositoriesResource}
 *
 * @since 3.9
 */
@Api(value = "Repositories")
public interface RepositoriesResourceDoc
{
  @ApiOperation("List repositories")
  List<RepositoryXO> getRepositories();
}
