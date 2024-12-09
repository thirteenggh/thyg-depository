package org.sonatype.nexus.repository.storage;

import org.sonatype.nexus.repository.security.VariableResolverAdapter;
import org.sonatype.nexus.selector.VariableSource;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Generate a variable source from a persisted asset, to be used for content selector evaluation
 *
 * @since 3.22
 */
public interface AssetVariableResolver
    extends VariableResolverAdapter
{
  VariableSource fromDocument(ODocument document);

  VariableSource fromAsset(Asset asset);
}
