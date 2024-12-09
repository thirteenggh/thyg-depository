package org.sonatype.nexus.common.app;

import java.util.List;

import static java.lang.String.format;

/**
 * Summary object representing read-only state.
 *
 * @since 3.21
 */
public class ReadOnlyState
{
  private final List<FreezeRequest> state;

  private final boolean detailed;

  public ReadOnlyState(final List<FreezeRequest> state, final boolean detailed) {
    this.state = state;
    this.detailed = detailed;
  }

  public boolean isFrozen() {
    return !state.isEmpty();
  }

  public String getSummaryReason() {
    if (!detailed || state.isEmpty()) {
      return "";
    }

    return "Requested by " + state.stream()
        .filter(r -> !r.token().isPresent())
        .findAny()
        .map(u -> format("an administrator at %s", u.frozenAt().toString("yyyy-MM-dd HH:mm:ss ZZ")))
        .orElse(format("%s running system task(s)", state.size()));
  }

  public boolean isSystemInitiated() {
    return state.stream().anyMatch(r -> r.token().isPresent());
  }
}
