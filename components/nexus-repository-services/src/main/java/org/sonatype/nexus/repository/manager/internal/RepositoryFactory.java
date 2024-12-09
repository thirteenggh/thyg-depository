package org.sonatype.nexus.repository.manager.internal;

import org.sonatype.nexus.repository.Format;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.Type;

/**
 * Repository factory.
 *
 * @since 3.0
 */
public interface RepositoryFactory
{
  Repository create(Type type, Format format);
}
