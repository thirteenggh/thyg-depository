package org.sonatype.nexus.repository.npm.internal.orient

import javax.annotation.Nonnull
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.view.Context
import org.sonatype.nexus.repository.view.Response
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State

import static java.util.Objects.isNull
import static org.sonatype.nexus.repository.npm.internal.NpmResponses.notFound
import static org.sonatype.nexus.repository.group.GroupHandler.DispatchedRepositories

/**
 * Merge dist tag results from all member repositories.
 *
 * @since 3.19
 */
@Named
@Singleton
class OrientNpmGroupDistTagsHandler
    extends OrientNpmGroupHandler
{
  @Override
  protected Response doGet(@Nonnull final Context context,
                           @Nonnull final DispatchedRepositories dispatched)
      throws Exception
  {
    log.debug '[getDistTags] group repository: {} tokens: {}',
        context.repository.name,
        context.attributes.require(State.class).tokens

    OrientNpmGroupDataFacet groupFacet = getNpmGroupFacet(context)

    // Dispatch requests to members to trigger update events and group cache invalidation when a member has changed
    Map<Repository, Response> responses = getResponses(context, dispatched, groupFacet)

    if (isNull(responses) || responses.isEmpty()) {
      return notFound("Not found")
    }

    return NpmFacetUtils.mergeDistTagResponse(responses)
  }
}
