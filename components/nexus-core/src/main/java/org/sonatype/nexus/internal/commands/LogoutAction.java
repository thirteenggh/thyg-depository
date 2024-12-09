package org.sonatype.nexus.internal.commands;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.security.SecurityHelper;

import org.apache.karaf.shell.api.action.Action;
import org.apache.karaf.shell.api.action.Command;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An action to logout the currently logged in user in the console.
 *
 * @since 3.3
 */
@Named
@Command(name = "logout", scope = "nexus", description = "Remove a user from context")
public class LogoutAction
    implements Action
{
  private final SecurityHelper securityHelper;

  @Inject
  public LogoutAction(final SecurityHelper securityHelper) {
    this.securityHelper = checkNotNull(securityHelper);
  }

  @Override
  public Object execute() throws Exception {
    securityHelper.subject().logout();
    return null;
  }
}

