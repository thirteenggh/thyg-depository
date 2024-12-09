package org.sonatype.nexus.internal.script

import javax.inject.Inject
import javax.inject.Named
import javax.script.ScriptContext

import org.sonatype.goodies.common.ComponentSupport
import org.sonatype.nexus.commands.SessionAware
import org.sonatype.nexus.common.script.ScriptService

import org.apache.karaf.shell.api.action.Action
import org.apache.karaf.shell.api.action.Argument
import org.apache.karaf.shell.api.action.Command
import org.apache.karaf.shell.api.action.Option
import org.apache.karaf.shell.api.console.Session
import org.slf4j.LoggerFactory

import static com.google.common.base.Preconditions.checkState

/**
 * Action to execute a script.
 *
 * @since 3.0
 */
@Named
@Command(name = 'script', scope = 'nexus', description = 'Execute a script')
class ScriptAction
    extends ComponentSupport
    implements Action, SessionAware
{
  @Inject
  ScriptService scripts

  @Option(name = '-l', aliases = ['--language'], required = false, description = 'Script language', valueToShowInHelp = ScriptEngineManagerProvider.DEFAULT_LANGUAGE)
  String language

  @Option(name = '-f', aliases = ['--file'], required = false, description = 'Script file')
  File file

  @Option(name = '-u', aliases = ['--url'], required = false, description = 'Script URL')
  URL url

  @Option(name = '-e', aliases = ['--expression'], required = false, description = 'Script expression')
  String expression

  @Argument(name = 'args', index = 0, multiValued = true, required = false, description = 'Script arguments')
  List<String> args

  Session session

  public void setSession(Session session) {
    this.session = session
  }

  // TODO: consider sub-shell support, but perhaps via another command?

  @Override
  public Object execute() throws Exception {
    // ensure only one source was configured
    def sources = 0
    [file, url, expression].each {
      if (it != null) {
        sources++
      }
    }
    checkState(sources == 1, 'One (and only one) of --file, --url or --expression must be specified')

    // resolve source text
    String source = null
    if (file != null) {
      log.debug "Source file: $file"
      // TODO: consider resolving language from file name
      source = file.text
    }
    else if (url != null) {
      log.debug "Source URL: $url"
      // TODO: consider resolving language from file name, or content-type
      source = url.text
    }
    else if (expression != null) {
      log.debug 'Source expression'
      source = expression
    }
    // should never happen due to assert above
    checkState(source != null, 'No source available')

    language = language ?: ScriptEngineManagerProvider.DEFAULT_LANGUAGE

    // construct new context for execution
    ScriptContext context = scripts.createContext(language)

    // adapt to session streams
    context.reader = session.keyboard.newReader()
    context.writer = context.errorWriter = session.console.newWriter()

    // customize scope for execution
    scripts.customizeBindings(context,
        [
            log: LoggerFactory.getLogger(ScriptAction),
            session: session,
            args: args
        ]
    )

    // execution script
    log.debug "Evaluating script: $source"
    def result = scripts.eval(language, source, context)
    log.debug "Result: $result"

    return result
  }
}
