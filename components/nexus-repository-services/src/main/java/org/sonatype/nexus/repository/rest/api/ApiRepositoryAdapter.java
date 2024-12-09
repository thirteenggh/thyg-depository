package org.sonatype.nexus.repository.rest.api;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.rest.api.model.AbstractApiRepository;

/**
 * Formats can implement this interface to supply custom objects to use in the RepositoryApiResource.
 *
 * @since 3.20
 */
public interface ApiRepositoryAdapter
{
  AbstractApiRepository adapt(Repository repository);
}
