package org.sonatype.nexus.repository.pypi.internal;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.group.GroupHandler;
import org.sonatype.nexus.repository.http.HttpResponses;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.repository.view.ContentTypes;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.Response;
import org.sonatype.nexus.repository.view.payloads.StringPayload;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.repository.http.HttpMethods.POST;
import static org.sonatype.nexus.repository.pypi.internal.PyPiSearchUtils.buildSearchResponse;
import static org.sonatype.nexus.repository.pypi.internal.PyPiSearchUtils.parseSearchResponse;

/**
 * Support for merging PyPI XML-RPC search results together.
 *
 * @since 3.1
 */
@Named
@Singleton
class SearchGroupHandler
    extends GroupHandler
{
  @Nonnull
  @Override
  public Response handle(@Nonnull final Context context) throws Exception {
    checkNotNull(context);
    final String method = context.getRequest().getAction();
    if (POST.equals(method)) {
      final DispatchedRepositories dispatched = context.getRequest().getAttributes()
          .getOrCreate(DispatchedRepositories.class);
      return doPost(context, dispatched);
    }
    return HttpResponses.methodNotAllowed(method, POST);
  }

  protected Response doPost(@Nonnull final Context context,
                            @Nonnull final GroupHandler.DispatchedRepositories dispatched)
      throws Exception
  {
    checkNotNull(context);
    checkNotNull(dispatched);

    Context replayableContext = context.replayable();
    GroupFacet groupFacet = context.getRepository().facet(GroupFacet.class);

    Map<String, PyPiSearchResult> results = new LinkedHashMap<>();
    for (Entry<Repository, Response> entry : getAll(replayableContext, groupFacet.members(), dispatched).entrySet()) {
      Response response = entry.getValue();
      if (response.getStatus().getCode() == HttpStatus.OK && response.getPayload() != null) {
        processResponse(response, results);
      }
    }

    String response = buildSearchResponse(results.values());
    return HttpResponses.ok(new StringPayload(response, ContentTypes.TEXT_HTML));
  }

  /**
   * Processes a search response, adding any new entries to the map. If an entry exists already, it is left untouched
   * to preserve the ordering of results from member repositories.
   */
  private void processResponse(final Response response, final Map<String, PyPiSearchResult> results) throws Exception {
    checkNotNull(response);
    checkNotNull(results);
    Payload payload = checkNotNull(response.getPayload());
    try (InputStream in = payload.openInputStream()) {
      for (PyPiSearchResult result : parseSearchResponse(in)) {
        String key = result.getName() + " " + result.getVersion();
        if (!results.containsKey(key)) {
          results.put(key, result);
        }
      }
    }
  }

}
