package org.sonatype.nexus.repository.conda.internal.util;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.matchers.token.TokenMatcher.State;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.19
 */
public class CondaPathUtils
{
  public static final String RSS_XML = "rss.xml";

  public static final String INDEX_HTML = "index.html";

  public static final String CHANNELDATA_JSON = "channeldata.json";

  public static final String REPODATA_JSON = "repodata.json";

  public static final String REPODATA_JSON_BZ2 = "repodata.json.bz2";

  public static final String REPODATA2_JSON = "repodata2.json";

  public static final String TAR_BZ2_EXT = ".tar.bz2";

  public static final String CONDA_EXT = ".conda";

  private CondaPathUtils() {
  }

  public static State matcherState(final Context context) {
    return context.getAttributes().require(State.class);
  }

  public static String arch(final State state) {
    return match(state, "arch");
  }

  public static String name(final State state) {
    return match(state, "name");
  }

  public static String version(final State state) {
    return match(state, "version");
  }

  static String path(final State state) {
    return match(state, "path");
  }

  static String build(final State state) {
    return match(state, "build");
  }

  private static String format(final State state) {
    return match(state, "format");
  }

  private static String match(final State state, final String name) {
    checkNotNull(state);
    String result = state.getTokens().get(name);
    checkNotNull(result);
    return result;
  }

  public static String buildAssetPath(final State matcherState, final String assetName)
  {
    return String.format("%s/%s", path(matcherState), assetName);
  }

  public static String buildArchAssetPath(final State matcherState, final String assetName)
  {
    return String.format("%s/%s/%s", path(matcherState), arch(matcherState), assetName);
  }

  public static String buildCondaPackagePath(final State matcherState) {
    return String.format("%s/%s/%s-%s-%s%s",
        path(matcherState),
        arch(matcherState),
        name(matcherState),
        version(matcherState),
        build(matcherState),
        format(matcherState));
  }
}
