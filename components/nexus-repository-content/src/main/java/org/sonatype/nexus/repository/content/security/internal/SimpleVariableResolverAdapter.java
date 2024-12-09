package org.sonatype.nexus.repository.content.security.internal;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.security.AssetVariableResolverSupport;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import org.elasticsearch.search.lookup.SourceLookup;

/**
 * Simple implementation that will expose the path/format variable resolvers.
 *
 * @since 3.24
 */
@Named("simple")
@Singleton
public class SimpleVariableResolverAdapter
    extends AssetVariableResolverSupport
{
  @Override
  protected void addFromRequest(final VariableSourceBuilder builder, final Request request) {
    // no-op the simple impl just allows for the path/format variable resolvers in the support class
  }

  @Override
  protected void addFromSourceLookup(final VariableSourceBuilder builder,
                                     final SourceLookup sourceLookup,
                                     final Map<String, Object> asset)
  {
    // no-op the simple impl just allows for the path/format variable resolvers in the support class
  }

  @Override
  protected void addFromAsset(final VariableSourceBuilder builder, final FluentAsset asset) {
    // no-op the simple impl just allows for the path/format variable resolvers in the support class
  }
}
