package org.sonatype.nexus.repository.content.security;

import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.selector.VariableSource;

/**
 * Generate a variable source from a persisted asset, to be used for content selector evaluation
 *
 * @since 3.29
 */
public interface AssetVariableResolver
    extends VariableResolverAdapter
{
  VariableSource fromAsset(FluentAsset asset);
}
