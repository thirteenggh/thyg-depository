package org.sonatype.nexus.repository.search.index;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;

/**
 * Name search indexes by hashing the repository name; avoids characters not allowed by Elasticsearch.
 *
 * @since 3.25
 */
@Named
@Singleton
public class HashedNamingPolicy
    implements IndexNamingPolicy
{
  private final LoadingCache<String, String> cachedNames =
      CacheBuilder.newBuilder().weakKeys().build(CacheLoader.from(HashedNamingPolicy::hashedName));

  @Override
  public String indexName(final Repository repository) {
    return cachedNames.getUnchecked(repository.getName());
  }

  private static String hashedName(final String repositoryName) {
    return SHA1.function().hashUnencodedChars(repositoryName).toString();
  }
}
