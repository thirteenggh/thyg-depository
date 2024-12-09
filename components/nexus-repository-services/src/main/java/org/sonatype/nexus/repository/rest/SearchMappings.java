package org.sonatype.nexus.repository.rest;

/**
 * Provide a set of {@link SearchMapping}s.
 *
 * @since 3.7
 */
public interface SearchMappings
{
  Iterable<SearchMapping> get();
}
