package org.sonatype.nexus.repository.view.matchers.token;

import java.util.Map;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;
import org.sonatype.nexus.repository.view.Request;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A {@link Matcher} that examines the {@link Request#getPath() request path} and attempts to parse it using the
 * {@link TokenParser}.
 *
 * If there is a match, the tokens are stored in the context under key {@link TokenMatcher.State}.
 *
 * @since 3.0
 */
public class TokenMatcher
    extends ComponentSupport
    implements Matcher
{
  public interface State
  {
    String pattern();

    Map<String, String> getTokens();
  }

  private final TokenParser parser;

  private final String pattern;

  public TokenMatcher(final String pattern) {
    this.pattern = checkNotNull(pattern);
    this.parser = new TokenParser(pattern);
  }

  @Override
  public boolean matches(final Context context) {
    checkNotNull(context);

    String path = context.getRequest().getPath();
    log.debug("Matching: {}~={}", path, parser);
    final Map<String, String> tokens = parser.parse(path);
    if (tokens == null) {
      // There was no match.
      return false;
    }

    // matched expose tokens in context
    context.getAttributes().set(State.class, new State()
    {
      @Override
      public String pattern() {
        return pattern;
      }

      @Override
      public Map<String, String> getTokens() {
        return tokens;
      }
    });
    return true;
  }
}
