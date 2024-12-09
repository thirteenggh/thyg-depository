package org.sonatype.nexus.repository.rest;

import org.sonatype.nexus.repository.rest.api.ComponentXO;
import org.sonatype.nexus.repository.rest.internal.resources.SearchResource;

import org.elasticsearch.search.SearchHit;

/**
 * Extension point for the {@link SearchResource} class
 *
 * @since 3.8
 */
public interface SearchResourceExtension
{
  /**
   * Update the {@link ComponentXO} with data from the {@link SearchHit}
   */
  ComponentXO updateComponentXO(ComponentXO componentXO, SearchHit hit);
}
