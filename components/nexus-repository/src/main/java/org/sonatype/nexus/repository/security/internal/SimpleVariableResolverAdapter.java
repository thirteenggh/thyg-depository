package org.sonatype.nexus.repository.security.internal;

import java.util.Map;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetVariableResolver;
import org.sonatype.nexus.repository.storage.AssetVariableResolverSupport;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import com.orientechnologies.orient.core.record.impl.ODocument;
import org.elasticsearch.search.lookup.SourceLookup;

/**
 * Simple implementation that will expose the path/format variable resolvers
 *
 * @since 3.1
 */
@Named(SimpleVariableResolverAdapter.NAME)
@Priority(Integer.MAX_VALUE)
@Singleton
public class SimpleVariableResolverAdapter
    extends AssetVariableResolverSupport
    implements AssetVariableResolver
{
  static final String NAME = "simple";

  @Override
  protected void addFromRequest(final VariableSourceBuilder builder, final Request request) {
    //no-op the simple impl just allows for the path/format variable resolvers in the support class
  }

  @Override
  protected void addFromDocument(final VariableSourceBuilder builder, final ODocument document) {
    //no-op the simple impl just allows for the path/format variable resolvers in the support class
  }

  @Override
  protected void addFromAsset(final VariableSourceBuilder builder, final Asset asset) {
    //no-op the simple impl just allows for the path/format variable resolvers in the support class
  }

  @Override
  protected void addFromSourceLookup(final VariableSourceBuilder builder,
                                     final SourceLookup sourceLookup,
                                     final Map<String, Object> asset)
  {
    //no-op the simple impl just allows for the path/format variable resolvers in the support class
  }
}
