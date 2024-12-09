package org.sonatype.nexus.testsuite.testsupport.utility;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.pax.exam.NexusPaxExamSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.search.index.SearchIndexService;
import org.sonatype.nexus.repository.search.query.SearchQueryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Named
@Singleton
public class SearchTestHelper
{
  @Inject
  public SearchIndexService indexService;

  @Inject
  public SearchQueryService searchQueryService;

  @Inject
  public EventManager eventManager;

  /**
   * Waits for indexing to finish and makes sure any updates are available to search.
   *
   * General flow is component/asset events -> bulk index requests -> search indexing.
   */
  public void waitForSearch() throws Exception {
    NexusPaxExamSupport.waitFor(eventManager::isCalmPeriod);
    indexService.flush(false); // no need for full fsync here
    NexusPaxExamSupport.waitFor(indexService::isCalmPeriod);
  }

  public void verifyComponentExists(
      final WebTarget nexusSearchWebTarget,
      final Repository repository,
      final String name,
      final String version,
      final boolean exists) throws Exception
  {
    String repositoryName = repository.getName();
    List<Map<String, Object>> items = searchForComponent(nexusSearchWebTarget, repositoryName, name, version);
    assertThat(items.size(), is(exists ? 1 : 0));
  }

  public SearchQueryService queryService() {
    return searchQueryService;
  }

  @SuppressWarnings("unchecked")
  private List<Map<String, Object>> searchForComponent(
      final WebTarget nexusSearchUrl, final String repository,
      final String artifactId,
      final String version)
      throws Exception
  {
    waitForSearch();

    Response response = nexusSearchUrl
        .queryParam("repository", repository)
        .queryParam("maven.artifactId", artifactId)
        .queryParam("maven.baseVersion", version)
        .request()
        .buildGet()
        .invoke();

    Map<String, Object> map = response.readEntity(Map.class);
    return (List<Map<String, Object>>) map.get("items");
  }
}
