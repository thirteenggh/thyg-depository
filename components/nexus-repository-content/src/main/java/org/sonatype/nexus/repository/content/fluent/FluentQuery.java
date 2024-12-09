package org.sonatype.nexus.repository.content.fluent;

import javax.annotation.Nullable;

import org.sonatype.nexus.common.entity.Continuation;

/**
 * Fluent API to count/browse elements in a repository.
 *
 * @since 3.26
 */
public interface FluentQuery<T>
{
  /**
   * Count the elements in the repository that match the current query.
   */
  int count();

  /**
   * Browse through elements in the repository that match the current query.
   */
  Continuation<T> browse(int limit, @Nullable String continuationToken);
}
