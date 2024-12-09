package org.sonatype.nexus.repository;

import javax.inject.Inject;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.recipe.RouterBuilder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Support for {@link Recipe} implementations.
 *
 * @since 3.0
 */
public abstract class RecipeSupport
    extends ComponentSupport
    implements Recipe
{
  private final Format format;

  private final Type type;

  private BrowseUnsupportedHandler browseUnsupportedHandler;

  protected RecipeSupport(final Type type, final Format format) {
    this.type = checkNotNull(type);
    this.format = checkNotNull(format);
  }

  @Override
  public Format getFormat() {
    return format;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "format=" + format +
        ", type=" + type +
        '}';
  }

  @Inject
  public void setBrowseUnsupportedHandler(final BrowseUnsupportedHandler browseUnsupportedHandler) {
    this.browseUnsupportedHandler = checkNotNull(browseUnsupportedHandler);
  }

  /**
   * Adds route to redirect access directly with a browser to a handler with links to the repo's components and
   * assets.
   */
  protected void addBrowseUnsupportedRoute(RouterBuilder builder) {
    builder.route(browseUnsupportedHandler.getRoute());
  }

  @Override
  public boolean isFeatureEnabled() {
    return true;
  }
}
