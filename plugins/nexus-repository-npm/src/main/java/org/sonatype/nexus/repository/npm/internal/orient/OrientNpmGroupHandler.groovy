package org.sonatype.nexus.repository.npm.internal.orient

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.group.GroupHandler
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Response

import static org.sonatype.nexus.repository.http.HttpStatus.OK
/**
 * @since 3.19
 */
class OrientNpmGroupHandler
    extends GroupHandler
{
  protected Map<Repository, Response> getResponses(final Context context,
                                                   final GroupHandler.DispatchedRepositories dispatched,
                                                   final OrientNpmGroupDataFacet groupFacet)
  {
    // get all and filter for HTTP OK responses
    return getAll(context, groupFacet.members(), dispatched).findAll { k, v -> v.status.code == OK }
  }

  protected OrientNpmGroupDataFacet getNpmGroupFacet(final Context context) {
    return context.getRepository().facet(OrientNpmGroupDataFacet.class)
  }
}
