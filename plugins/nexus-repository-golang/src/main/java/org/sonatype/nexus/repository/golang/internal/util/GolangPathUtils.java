package org.sonatype.nexus.repository.golang.internal.util;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.golang.internal.metadata.GolangAttributes;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility methods for working with Go routes and paths.
 *
 * @since 3.17
 */
@Named
@Singleton
public class GolangPathUtils
{
  /**
   * Returns the module from a {@link
   * TokenMatcher.State}.
   */
  public String module(final TokenMatcher.State state) {
    return match(state, "module");
  }

  /**
   * Returns the version from a {@link TokenMatcher.State}.
   */
  public String version(final TokenMatcher.State state) {
    return match(state, "version");
  }

  /**
   * Returns the extension from a {@link TokenMatcher.State}.
   */
  public String extension(final TokenMatcher.State state) {
    return match(state, "extension");
  }

  /**
   * Builds a go asset path from a {@link TokenMatcher.State}.
   */
  public String assetPath(final TokenMatcher.State state) {
    String module = module(state);
    String version = version(state);
    String extension = extension(state);
    String fullPath = String.format("%s/@v/%s.%s", module, version, extension);

    return fullPath;
  }

  /**
   * Builds a go list path from a {@link TokenMatcher.State}.
   */
  public String listPath(final TokenMatcher.State state) {
    String module = module(state);

    return String.format("%s/@v/list", module);
  }

  /**
   * Builds a go latest path from a {@link TokenMatcher.State}.
   */
  public String latestPath(final TokenMatcher.State state) {
    String module = module(state);

    return String.format("%s/@latest", module);
  }

  /**
   * Utility method encapsulating getting a particular token by name from a matcher, including preconditions.
   */
  private String match(final TokenMatcher.State state, final String name) {
    checkNotNull(state);
    String result = state.getTokens().get(name);
    checkNotNull(result);
    return result;
  }

  /**
   * Returns the {@link TokenMatcher.State} for the content.
   */
  public TokenMatcher.State matcherState(final Context context) {
    return context.getAttributes().require(TokenMatcher.State.class);
  }

  /**
   * Returns the module name and version from a given {@link TokenMatcher.State}
   *
   * @param state
   * @return {@link GolangAttributes}
   */
  public GolangAttributes getAttributesFromMatcherState(final TokenMatcher.State state) {
    GolangAttributes golangAttributes = new GolangAttributes();
    golangAttributes.setModule(module(state));
    golangAttributes.setVersion(version(state));

    return golangAttributes;
  }
}
