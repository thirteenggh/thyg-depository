package org.sonatype.nexus.repository;

import javax.annotation.Nonnull;

/**
 * Repository recipe.
 *
 * @since 3.0
 */
public interface Recipe
{
  Type getType();

  Format getFormat();

  void apply(@Nonnull Repository repository) throws Exception;

  boolean isFeatureEnabled();
}
