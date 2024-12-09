package org.sonatype.nexus.internal.commands;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.app.FreezeService;

import com.google.common.annotations.VisibleForTesting;
import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;
import org.apache.karaf.shell.api.action.Option;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.internal.commands.FreezeAction.Mode.enable;

/**
 * An action to freeze and release the application.
 *
 * @since 3.2
 */
@Named
@Command(name = "freeze", scope = "nexus", description = "Freeze Nexus Repository Manager")
public class FreezeAction
    implements Action
{
  private final FreezeService freezeService;

  @Option(name = "-m", aliases = {"--mode"}, description = "Manage mode: enable or release (default enable)")
  Mode mode = enable;

  @VisibleForTesting
  enum Mode
  {
    enable, release //NOSONAR
  }

  @Inject
  public FreezeAction(final FreezeService freezeService) {
    this.freezeService = checkNotNull(freezeService);
  }

  @Override
  public Object execute() throws Exception {
    switch (mode) { //NOSONAR
      case enable:
        freezeService.requestFreeze("console request");
        break;
      case release:
        freezeService.cancelFreeze();
        break;
    }
    return null;
  }
}

