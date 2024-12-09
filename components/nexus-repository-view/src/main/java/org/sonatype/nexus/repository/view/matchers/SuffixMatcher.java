package org.sonatype.nexus.repository.view.matchers;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Suffix string matcher.
 *
 * @since 3.0
 */
public class SuffixMatcher
  extends ComponentSupport
  implements Matcher
{
  private final String suffix;

  private final boolean ignoreCase;

  @VisibleForTesting
  public SuffixMatcher(final String suffix, final boolean ignoreCase) {
    this.suffix = checkNotNull(suffix);
    this.ignoreCase = ignoreCase;
  }

  public SuffixMatcher(final String suffix) {
    this(suffix, false);
  }

  public SuffixMatcher ignoreCase(final boolean ignoreCase) {
    return new SuffixMatcher(suffix, ignoreCase);
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);
    String path = context.getRequest().getPath();
    log.debug("Matching: {} ends-with={} ignore-case: {}", path, suffix, ignoreCase);
    if (ignoreCase) {
      return Strings2.lower(path).endsWith(Strings2.lower(suffix));
    }
    else {
      return path.endsWith(suffix);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "suffix='" + suffix + '\'' +
        ", ignoreCase=" + ignoreCase +
        '}';
  }
}
