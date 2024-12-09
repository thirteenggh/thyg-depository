package org.sonatype.nexus.repository.view.matchers;

import java.util.List;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.view.Matcher;
import org.sonatype.nexus.repository.view.Request;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Arrays.asList;

/**
 * Matches if the {@link Request#getAction() request action} is in the allowed list.
 *
 * @since 3.0
 */
public class ActionMatcher
    extends ComponentSupport
    implements Matcher
{
  private final List<String> allowedActions;

  public ActionMatcher(final String... allowedActions) {
    checkNotNull(allowedActions);
    checkArgument(allowedActions.length > 0, "at least one allowed action must be specified");
    this.allowedActions = asList(allowedActions);
  }

  @Override
  public boolean matches(final Context context) {
    final String action = context.getRequest().getAction();
    return allowedActions.contains(action);
  }
}
