package org.sonatype.nexus.repository.manager;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.datastore.DataStoreUsageChecker;
import org.sonatype.nexus.repository.Recipe;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.config.Configuration;

/**
 * Repository manager.
 *
 * @since 3.0
 */
public interface RepositoryManager
  extends DataStoreUsageChecker, Lifecycle
{
  Iterable<Repository> browse();

  /**
   * @since 3.6.1
   */
  Iterable<Repository> browseForBlobStore(String blobStoreId);

  boolean exists(String name);

  @Nullable
  Repository get(String name);

  Repository create(Configuration configuration) throws Exception;

  Repository update(Configuration configuration) throws Exception;

  void delete(String name) throws Exception;

  boolean isBlobstoreUsed(String blobStoreName);

  long blobstoreUsageCount(String blobStoreName);

  List<String> findContainingGroups(String repositoryName);

  Stream<Repository> browseForCleanupPolicy(final String cleanupPolicyName);

  Collection<Recipe> getAllSupportedRecipes();

  /**
   * Create a new {@link Configuration} instance.
   *
   * @since 3.21
   */
  Configuration newConfiguration();
}
