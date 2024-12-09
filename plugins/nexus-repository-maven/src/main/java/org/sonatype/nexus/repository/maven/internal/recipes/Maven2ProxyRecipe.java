package org.sonatype.nexus.repository.maven.internal.recipes;

import org.sonatype.nexus.repository.Recipe;

/**
 * Maven 2 proxy repository recipe.
 *
 * @since 3.0
 */
public interface Maven2ProxyRecipe
    extends Recipe
{
  String NAME = "maven2-proxy";
}
