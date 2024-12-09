package org.sonatype.nexus.repository.security;

import java.util.Map;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.ConstantVariableResolver;
import org.sonatype.nexus.selector.PropertiesResolver;
import org.sonatype.nexus.selector.VariableSource;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import org.elasticsearch.search.lookup.SourceLookup;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Adapts different contexts to variable resolvers
 *
 * @since 3.1
 */
public abstract class VariableResolverAdapterSupport
  implements VariableResolverAdapter
{
  protected static final String NAME = "name";
  protected static final String PATH = "path";
  protected static final String FORMAT = "format";

  @Override
  public VariableSource fromRequest(final Request request, final Repository repository) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(request.getPath().substring(1), PATH));
    builder.addResolver(new ConstantVariableResolver(repository.getFormat().getValue(), FORMAT));
    addFromRequest(builder, request);

    return builder.build();
  }

  protected abstract void addFromRequest(VariableSourceBuilder builder, Request request);

  @Override
  public VariableSource fromCoordinates(final String format, final String path, final Map<String, String> coordinates) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(checkNotNull(path), PATH));
    builder.addResolver(new ConstantVariableResolver(checkNotNull(format), FORMAT));

    addCoordinates(builder, coordinates);
    return builder.build();
  }

  @Override
  public VariableSource fromSourceLookup(final SourceLookup sourceLookup, final Map<String, Object> asset) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(
        new ConstantVariableResolver(checkNotNull((String) asset.get(NAME)), PATH));
    builder.addResolver(
        new ConstantVariableResolver(checkNotNull(sourceLookup.get(FORMAT)), FORMAT));
    addFromSourceLookup(builder, sourceLookup, asset);

    return builder.build();
  }

  protected abstract void addFromSourceLookup(VariableSourceBuilder builder,
                                              SourceLookup sourceLookup,
                                              Map<String, Object> asset);

  protected void addCoordinates(final VariableSourceBuilder builder, final Map<String, String> coordinates) {
    builder.addResolver(new PropertiesResolver<>("coordinate", coordinates));
  }

  @Override
  public VariableSource fromPath(final String path, final String format) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(path, PATH));
    builder.addResolver(new ConstantVariableResolver(format, FORMAT));

    return builder.build();
  }
}
