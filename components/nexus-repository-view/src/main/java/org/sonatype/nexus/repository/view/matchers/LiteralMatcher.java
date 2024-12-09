package org.sonatype.nexus.repository.view.matchers;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Literal string matcher.
 *
 * @since 3.0
 */
public class LiteralMatcher
  extends ComponentSupport
  implements Matcher
{
  private final String literal;

  private final boolean ignoreCase;

  public LiteralMatcher(final String literal, final boolean ignoreCase) {
    this.literal = checkNotNull(literal);
    this.ignoreCase = ignoreCase;
  }

  public LiteralMatcher(final String literal) {
    this(literal, true);
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);
    String path = context.getRequest().getPath();
    log.debug("Matching: {}={} ignore-case: {}", path, literal, ignoreCase);
    if (ignoreCase) {
      return path.equalsIgnoreCase(literal);
    }
    else {
      return path.equals(literal);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "literal='" + literal + '\'' +
        ", ignoreCase=" + ignoreCase +
        '}';
  }
}
