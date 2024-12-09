package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.repository.security.VariableResolverAdapterSupport;
import org.sonatype.nexus.selector.ConstantVariableResolver;
import org.sonatype.nexus.selector.VariableSource;
import org.sonatype.nexus.selector.VariableSourceBuilder;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Adapts persisted assets to variable resolvers.
 *
 * @since 3.22
 */
public abstract class AssetVariableResolverSupport
    extends VariableResolverAdapterSupport
    implements AssetVariableResolver
{
  public VariableSource fromDocument(final ODocument document) {
    String path = document.field(AssetEntityAdapter.P_NAME, String.class);
    String format = document.field(AssetEntityAdapter.P_FORMAT, String.class);

    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(path, PATH));
    builder.addResolver(new ConstantVariableResolver(format, FORMAT));
    addFromDocument(builder, document);

    return builder.build();
  }

  protected abstract void addFromDocument(VariableSourceBuilder builder, ODocument document);

  public VariableSource fromAsset(final Asset asset) {
    VariableSourceBuilder builder = new VariableSourceBuilder();
    builder.addResolver(new ConstantVariableResolver(asset.name(), PATH));
    builder.addResolver(new ConstantVariableResolver(asset.format(), FORMAT));
    addFromAsset(builder, asset);

    return builder.build();
  }

  protected abstract void addFromAsset(VariableSourceBuilder builder, Asset asset);
}
