package org.sonatype.nexus.commands;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.console.Session;

/**
 * Allows {@link Action} instances to be aware of the current session.
 * 
 * @since 3.0
 */
public interface SessionAware
{
  void setSession(Session session);
}
