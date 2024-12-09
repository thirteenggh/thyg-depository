package org.sonatype.nexus.repository.content.fluent.internal;

import java.util.Map;

import org.sonatype.nexus.common.entity.Continuation;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.fluent.FluentQuery;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link FluentQuery} implementation for {@link FluentComponent}s.
 *
 * @since 3.26
 */
public class FluentComponentQueryImpl
    implements FluentQuery<FluentComponent>
{
  private final FluentComponentsImpl components;

  private final String kind;

  private final String filter;

  private final Map<String, Object> filterParams;

  FluentComponentQueryImpl(final FluentComponentsImpl components, final String kind) {
    this.components = checkNotNull(components);
    this.kind = checkNotNull(kind);
    this.filter = null;
    this.filterParams = null;
  }

  FluentComponentQueryImpl(final FluentComponentsImpl components,
                           final String filter,
                           final Map<String, Object> filterParams)
  {
    this.components = checkNotNull(components);
    this.kind = null;
    this.filter = checkNotNull(filter);
    this.filterParams = checkNotNull(filterParams);
  }

  @Override
  public int count() {
    return components.doCount(kind, filter, filterParams);
  }

  @Override
  public Continuation<FluentComponent> browse(final int limit, final String continuationToken) {
    return components.doBrowse(limit, continuationToken, kind, filter, filterParams);
  }
}
