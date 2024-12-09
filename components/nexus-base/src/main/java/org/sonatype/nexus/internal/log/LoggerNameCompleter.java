package org.sonatype.nexus.internal.log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.log.LogManager;

import org.apache.karaf.shell.api.console.CommandLine;
import org.apache.karaf.shell.api.console.Completer;
import org.apache.karaf.shell.api.console.Session;
import org.apache.karaf.shell.support.completers.StringsCompleter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Logger name completer.
 *
 * @since 3.0
 */
@Named
public class LoggerNameCompleter
    implements Completer
{
  private final LogManager logManager;

  @Inject
  public LoggerNameCompleter(final LogManager logManager) {
    this.logManager = checkNotNull(logManager);
  }

  @Override
  public int complete(final Session session, final CommandLine commandLine, final List<String> candidates) {
    return new StringsCompleter(logManager.getLoggers().keySet()).complete(session, commandLine, candidates);
  }
}
