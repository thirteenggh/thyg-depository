package org.sonatype.nexus.repository.security;

import java.util.Map;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Request;
import org.sonatype.nexus.selector.VariableSource;

import org.elasticsearch.search.lookup.SourceLookup;

/**
 * Generate a variable source from a context, to be used for content selector evaluation
 * @since 3.1
 */
public interface VariableResolverAdapter
{
  VariableSource fromRequest(Request request, Repository repository);

  /**
   * Creates a {@link VariableSource} from an ES-indexed asset.
   */
  VariableSource fromSourceLookup(SourceLookup sourceLookup, Map<String, Object> asset);

  /**
   * @since 3.8
   */
  VariableSource fromCoordinates(String format, String path, Map<String, String> coordinates);

  /**
   * @since 3.18
   */
  VariableSource fromPath(String path, String format);
}
