package org.sonatype.nexus.internal.log

import javax.inject.Inject
import javax.inject.Named

import org.sonatype.nexus.common.log.LogManager

import org.apache.karaf.shell.api.action.Action
import org.apache.karaf.shell.api.action.Command
import org.apache.karaf.shell.api.action.Option
import org.apache.karaf.shell.support.table.ShellTable

/**
 * Action to display configured loggers.
 *
 * @since 3.0
 */
@Named
@Command(name='loggers', scope = 'nexus', description = 'Display loggers')
class LoggersAction
  implements Action
{
  @Inject
  LogManager logManager

  @Option(name='-r', aliases = ['--reset'], description = 'Reset loggers')
  Boolean reset

  @Override
  public Object execute() throws Exception {
    if (reset) {
      logManager.resetLoggers()
      return null
    }

    def table = new ShellTable()
    table.column('Name')
    table.column('Level').alignRight()

    def loggers = logManager.loggers
    loggers.keySet().sort().each { name ->
      table.addRow().addContent(name, loggers[name])
    }

    table.print System.out
    return null
  }
}
