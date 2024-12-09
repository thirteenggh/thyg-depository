package org.sonatype.nexus.repository.view.matchers.logic;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Logical NOT matcher.
 *
 * @since 3.0
 *
 * @see LogicMatchers
 */
public class NotMatcher
  extends ComponentSupport
  implements Matcher
{
  private final Matcher matcher;

  @VisibleForTesting
  public NotMatcher(final Matcher matcher) {
    this.matcher = checkNotNull(matcher);
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);
    log.debug("Matching: NOT {}", matcher);
    return !matcher.matches(context);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "matcher=" + matcher +
        '}';
  }
}
