package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.search.query.SearchQueryService;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.transform;
import static java.util.Collections.emptyList;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.sonatype.nexus.repository.search.query.RepositoryQueryBuilder.unrestricted;

/**
 * @since 3.29
 */
public abstract class AbstractSearchCleanupComponentBrowse
    extends ComponentSupport
{
  private static final String NAME = "name";

  private static final String VERSION = "version";

  private static final String GROUP = "group";

  private final SearchQueryService searchQueryService;

  private final MetricRegistry metricRegistry;

  private final Map<String, Timer> timers = new ConcurrentHashMap<>();

  private final Map<String, CriteriaAppender> criteriaAppenders;

  protected AbstractSearchCleanupComponentBrowse(
      final Map<String, CriteriaAppender> criteriaAppenders,
      final SearchQueryService searchQueryService,
      final MetricRegistry metricRegistry)
  {
    this.criteriaAppenders = checkNotNull(criteriaAppenders);
    this.searchQueryService = checkNotNull(searchQueryService);
    this.metricRegistry = checkNotNull(metricRegistry);
  }

  public Iterable<EntityId> browse(final CleanupPolicy policy, final Repository repository) {
    if (policy.getCriteria().isEmpty()) {
      return emptyList();
    }

    QueryBuilder query = convertPolicyToQuery(policy);

    log.debug("Searching for components to cleanup using policy {}", policy);

    return transform(invokeSearch(policy, repository, query),
        searchHit -> new DetachedEntityId(searchHit.getId()));
  }

  private Iterable<SearchHit> invokeSearch(final CleanupPolicy policy,
                                           final Repository repository,
                                           final QueryBuilder query)
  {
    long start = System.nanoTime();

    try {
      return searchQueryService.browse(unrestricted(query).inRepositories(repository));
    }
    finally {
      updateTimer(policy, repository, System.nanoTime() - start);
    }
  }

  protected SearchResponse invokeSearchByPage(final CleanupPolicy policy,
                                            final Repository repository,
                                            final QueryOptions options,
                                            final QueryBuilder query)
  {
    long start = System.nanoTime();

    try {
      return searchQueryService.search(
          unrestricted(query)
              .sortBy(getSort(options.getSortProperty(), options.getSortDirection()))
              .inRepositories(repository),
          options.getStart(),
          options.getLimit());
    }
    finally {
      updateTimer(policy, repository, System.nanoTime() - start);
    }
  }

  private void updateTimer(final CleanupPolicy policy, final Repository repository, final long value) {
    Timer timer = timers.computeIfAbsent(policy.getName() + "." + repository.getName(), key ->
        metricRegistry.timer(getTimerName(policy, repository)));
    timer.update(value, NANOSECONDS);

    if (log.isTraceEnabled()) {
      log.trace("Cleanup policy {} search on {} has criteria {} and took {}ms", policy.getName(), repository.getName(),
          policy.getCriteria(), value);
    }
  }

  private String getTimerName(final CleanupPolicy policy, final Repository repository) {
    return getClass().getName().replaceAll("\\$.*", "") + '.' + policy.getName() + "." + repository.getName() +
        ".timer";
  }

  protected QueryBuilder convertPolicyToQuery(final CleanupPolicy policy, final QueryOptions options) {
    BoolQueryBuilder queryBuilder = convertPolicyToQuery(policy);

    if (isNullOrEmpty(options.getFilter())) {
      return queryBuilder;
    }

    QueryStringQueryBuilder stringQueryBuilder =
        QueryBuilders.queryStringQuery(addWildcard(options.getFilter()))
            .field(NAME).field(GROUP).field(VERSION);
    return queryBuilder.must(stringQueryBuilder);
  }

  private String addWildcard(final String filter) {
    return filter + "*";
  }

  private BoolQueryBuilder convertPolicyToQuery(final CleanupPolicy policy) {
    BoolQueryBuilder query = boolQuery().must(matchAllQuery());

    for (Entry<String, String> criteria : policy.getCriteria().entrySet()) {
      addCriteria(query, criteria.getKey(), criteria.getValue());
    }

    return query;
  }

  private void addCriteria(final BoolQueryBuilder query, final String key, final String value) {
    if (!criteriaAppenders.containsKey(key)) {
      throw new UnsupportedOperationException("Criteria of type " + key + " is not supported");
    }

    criteriaAppenders.get(key).append(query, value);
  }

  private SortBuilder getSort(final String sortProperty, final String sortDirection) {
    return SortBuilders.fieldSort(sortProperty).order(SortOrder.valueOf(sortDirection.toUpperCase()));
  }

}
