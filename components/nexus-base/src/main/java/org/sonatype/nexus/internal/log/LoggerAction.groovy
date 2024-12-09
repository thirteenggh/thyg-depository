package org.sonatype.nexus.internal.log

import javax.inject.Inject
import javax.inject.Named

import org.sonatype.nexus.common.log.LogManager
import org.sonatype.nexus.common.log.LoggerLevel

import org.apache.karaf.shell.api.action.Action
import org.apache.karaf.shell.api.action.Argument
import org.apache.karaf.shell.api.action.Command
import org.apache.karaf.shell.api.action.Completion
import org.apache.karaf.shell.api.action.Option

/**
 * Action to set or display logger level.
 *
 * @since 3.0
 */
@Named
@Command(name='logger', scope = 'nexus', description = 'Set or display logger level')
class LoggerAction
  implements Action
{
  @Inject
  LogManager logManager

  @Option(name='-d', aliases = ['--delete'], description = 'Delete logger')
  Boolean delete

  @Option(name='-e', aliases = ['--effective'], description = 'Return effective logger level')
  Boolean effective

  // FIXME: Presently a strict flag is set on some Karaf stuff related to completion, and
  // FIXME: ... unless you use the logger-name completer, then the level completion will not get picked up
  // FIXME: ... unsure where, but looks like if a completer fails, all completers after it are ignored

  @Argument(name="name", index = 0, required = true, description = 'Logger name')
  @Completion(LoggerNameCompleter)
  String name

  @Argument(name="level", index = 1, description = 'Logger level')
  LoggerLevel level

  @Override
  public Object execute() throws Exception {
    if (delete) {
      logManager.unsetLoggerLevel(name)
    }
    else if (level) {
      logManager.setLoggerLevel(name, level)
    }
    else {
      if (effective) {
        level = logManager.getLoggerEffectiveLevel(name)
      }
      else {
        level = logManager.getLoggerLevel(name)
      }

      if (level) {
        println "$name = $level"
      }
      else {
        println "$name is not set"
      }
    }
    return null
  }
}
