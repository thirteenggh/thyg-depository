package org.sonatype.nexus.internal.commands;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.app.ManagedLifecycle.Phase;
import org.sonatype.nexus.common.app.ManagedLifecycleManager;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Argument;
import org.apache.karaf.shell.api.action.Command;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Command to manage the Nexus application lifecycle.
 *
 * @since 3.16
 */
@Named
@Command(name = "lifecyclePhase", scope = "nexus", description = "Move the Nexus application lifecycle to the given phase")
public class LifecyclePhaseAction
    implements Action
{
  private final ManagedLifecycleManager lifecycleManager;

  @Argument(description = "The phase to move to")
  Phase phase;

  @Inject
  public LifecyclePhaseAction(final ManagedLifecycleManager lifecycleManager) {
    this.lifecycleManager = checkNotNull(lifecycleManager);
  }

  @Override
  public Object execute() throws Exception {
    if (phase != null) {
      lifecycleManager.to(phase);
    }
    return lifecycleManager.getCurrentPhase().name();
  }
}
