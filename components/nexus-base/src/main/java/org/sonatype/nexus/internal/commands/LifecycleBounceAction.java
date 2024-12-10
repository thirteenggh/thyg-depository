package org.sonatype.nexus.internal.commands;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Command(name = "lifecycleBounce", scope = "nexus", description = "Re-runs all phases from the given phase to the current phase")
public class LifecycleBounceAction
    implements Action
{
  private final ManagedLifecycleManager lifecycleManager;

  @Argument(description = "The phase to bounce")
  Phase phase;

  @Inject
  public LifecycleBounceAction(final ManagedLifecycleManager lifecycleManager) {
    this.lifecycleManager = checkNotNull(lifecycleManager);
  }

  @Override
  public Object execute() throws Exception {
    if (phase != null) {
      lifecycleManager.bounce(phase);
    }
    return lifecycleManager.getCurrentPhase().name();
  }
}
