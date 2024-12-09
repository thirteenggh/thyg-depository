package org.sonatype.nexus.repository.content.security;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.security.VariableResolverAdapterSupport;
import org.sonatype.nexus.selector.ConstantVariableResolver;
import org.sonatype.nexus.selector.VariableSource;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import static org.apache.commons.lang3.StringUtils.stripStart;

/**
 * Adapts persisted assets to variable resolvers.
 *
 * @since 3.29
 */
public abstract class AssetVariableResolverSupport
    extends VariableResolverAdapterSupport
    implements AssetVariableResolver
{
  @Override
  public VariableSource fromAsset(final FluentAsset asset) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(stripStart(asset.path(), "/"), PATH));
    builder.addResolver(new ConstantVariableResolver(asset.repository().getFormat().getValue(), FORMAT));
    addFromAsset(builder, asset);

    return builder.build();
  }

  protected abstract void addFromAsset(VariableSourceBuilder builder, FluentAsset asset);
}
