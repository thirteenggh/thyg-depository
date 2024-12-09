package org.sonatype.nexus.repository.maven.internal.recipes;

import org.sonatype.nexus.repository.Recipe;

/**
 * Maven 2 group repository recipe.
 *
 * @since 3.0
 */
public interface Maven2GroupRecipe
    extends Recipe
{
  String NAME = "maven2-group";
}
