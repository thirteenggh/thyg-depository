package org.sonatype.nexus.orient.internal.freeze;

import java.util.List;

import org.sonatype.nexus.orient.freeze.FreezeRequest;
import org.sonatype.nexus.orient.freeze.ReadOnlyState;

import static java.lang.String.format;
import static org.sonatype.nexus.orient.freeze.FreezeRequest.InitiatorType.SYSTEM;
import static org.sonatype.nexus.orient.freeze.FreezeRequest.InitiatorType.USER_INITIATED;

/**
 * Default {@link ReadOnlyState} implementation.
 */
class DefaultReadOnlyState
    implements ReadOnlyState
{

  private final List<FreezeRequest> state;

  private final boolean authorized;

  DefaultReadOnlyState(final List<FreezeRequest> state, final boolean authorized) {
    this.state = state;
    this.authorized = authorized;
  }

  @Override
  public boolean isFrozen() {
    return !state.isEmpty();
  }

  @Override
  public String getSummaryReason() {
    if (!authorized || state.isEmpty()) {
      return "";
    }

    return state.stream().filter(r -> USER_INITIATED.equals(r.getInitiatorType())).findAny()
        .map(u -> format("activated by an administrator at %s", u.getTimestamp().toString("yyyy-MM-dd HH:mm:ss ZZ")))
        .orElse(format("activated by %s running system task(s)", state.size()));
  }

  @Override
  public boolean isSystemInitiated() {
    return state.stream().anyMatch(r -> SYSTEM.equals(r.getInitiatorType()));
  }
}
