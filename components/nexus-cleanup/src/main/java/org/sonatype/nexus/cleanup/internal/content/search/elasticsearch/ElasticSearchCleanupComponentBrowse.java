package org.sonatype.nexus.cleanup.internal.content.search.elasticsearch;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.cleanup.internal.search.elasticsearch.AbstractSearchCleanupComponentBrowse;
import org.sonatype.nexus.cleanup.internal.search.elasticsearch.CriteriaAppender;
import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.extdirect.model.PagedResponse;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.search.query.SearchQueryService;

import com.codahale.metrics.MetricRegistry;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

/**
 * Finds components for cleanup using Elastic Search.
 *
 * @since 3.29
 */
@Named
@Singleton
public class ElasticSearchCleanupComponentBrowse
    extends AbstractSearchCleanupComponentBrowse
    implements CleanupComponentBrowse
{

  @Inject
  public ElasticSearchCleanupComponentBrowse(final Map<String, CriteriaAppender> criteriaAppenders,
                                             final SearchQueryService searchQueryService,
                                             final MetricRegistry metricRegistry)
  {
    super(criteriaAppenders, searchQueryService, metricRegistry);
  }

  @Override
  public PagedResponse<Component> browseByPage(final CleanupPolicy policy,
                                               final Repository repository,
                                               final QueryOptions options)
  {
    checkNotNull(options.getStart());
    checkNotNull(options.getLimit());

    QueryBuilder query = convertPolicyToQuery(policy, options);

    log.debug("Searching for components to cleanup using policy {}", policy);

    SearchResponse searchResponse = invokeSearchByPage(policy, repository, options, query);

    ContentFacet content = repository.facet(ContentFacet.class);

    List<Component> components = stream(searchResponse.getHits().spliterator(), false)
        .map(searchHit -> content.components().find(new DetachedEntityId(searchHit.getId())))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());

    return new PagedResponse<>(searchResponse.getHits().getTotalHits(), components);
  }
}
