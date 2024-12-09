package org.sonatype.nexus.repository.view.matchers;

import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

/**
 * Never matcher.
 *
 * @since 3.0
 */
public class NeverMatcher
  implements Matcher
{
  @Override
  public boolean matches(final Context context) {
    return false;
  }
}
