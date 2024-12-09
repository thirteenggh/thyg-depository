package org.sonatype.repository.helm.internal.util;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;
import org.sonatype.repository.helm.internal.metadata.IndexYamlAbsoluteUrlRewriter;

import org.apache.commons.io.FilenameUtils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility methods for working with Helm routes and paths.
 *
 * @since 3.28
 */
@Named
@Singleton
public class HelmPathUtils
    extends ComponentSupport
{
  private final IndexYamlAbsoluteUrlRewriter urlRewriter;

  @Inject
  public HelmPathUtils(final IndexYamlAbsoluteUrlRewriter urlRewriter) {
    this.urlRewriter = checkNotNull(urlRewriter);
  }

  /**
   * Returns the filename from a {@link State}.
   */
  public String filename(final State state) {
    return match(state, "filename");
  }

  public String contentFilePath(
      final State state,
      final boolean isForwardingSlash)
  {
    String filename = filename(state);
    return isForwardingSlash ? String.format("/%s", filename) : filename;
  }

  @Nullable
  public String contentFileUrl(
      final String filename,
      final Content indexYaml)
  {
    String chartName = getChartName(filename);
    String chartVersion = getChartVersion(filename);
    return urlRewriter.getFirstUrl(indexYaml, chartName, chartVersion).orElse(null);
  }

  public String getChartName(final String filename) {
    String filenameWithoutExt = FilenameUtils.removeExtension(filename);
    int index = filenameWithoutExt.lastIndexOf('-');
    return filenameWithoutExt.substring(0, index);
  }

  public String getChartVersion(final String filename) {
    String filenameWithoutExt = FilenameUtils.removeExtension(filename);
    int index = filenameWithoutExt.lastIndexOf('-');
    return filenameWithoutExt.substring(index + 1);
  }

  public String extension(final State state) {
    return match(state, "extension");
  }

  /**
   * Utility method encapsulating getting a particular token by name from a matcher, including preconditions.
   */
  private String match(final State state, final String name) {
    checkNotNull(state);
    String result = state.getTokens().get(name);
    checkNotNull(result);
    return result;
  }

  /**
   * Returns the {@link State} for the content.
   */
  public State matcherState(final Context context) {
    return context.getAttributes().require(State.class);
  }

  public String buildAssetPath(final State matcherState) {
    String filename = filename(matcherState);
    String extension = extension(matcherState);
    return filename + "." + extension;
  }

  public String buildContentAssetPath(final State matcherState) {
    return String.format("/%s", buildAssetPath(matcherState));
  }
}
