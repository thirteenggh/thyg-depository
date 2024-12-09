package org.sonatype.nexus.commands;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.console.Command;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.impl.action.command.ActionCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides support for Karaf {@link Action} {@link Command} implementations.
 * 
 * @since 3.0
 */
public abstract class CommandSupport
    extends ActionCommand
{
  protected final Logger log = LoggerFactory.getLogger(getClass());

  public CommandSupport(final Class<? extends Action> actionClass) {
    super(null, actionClass);
  }

  @Override
  protected abstract Action createNewAction(Session session);

  @Override
  protected abstract void releaseAction(Action action) throws Exception;
}
