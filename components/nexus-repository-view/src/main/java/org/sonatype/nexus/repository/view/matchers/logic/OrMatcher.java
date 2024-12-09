package org.sonatype.nexus.repository.view.matchers.logic;

import java.util.List;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Logical OR matcher.
 *
 * @since 3.0
 *
 * @see LogicMatchers
 */
public class OrMatcher
    extends ComponentSupport
    implements Matcher
{
  private final List<Matcher> matchers;

  @VisibleForTesting
  public OrMatcher(final List<Matcher> matchers) {
    this.matchers = checkNotNull(matchers);
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);
    if (log.isDebugEnabled()) {
      log.debug("Matching: {}", Joiner.on(" OR ").join(matchers));
    }
    for (Matcher matcher : matchers) {
      boolean matched = matcher.matches(context);
      if (matched) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "matchers=" + matchers +
        '}';
  }
}
