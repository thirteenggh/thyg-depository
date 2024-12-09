package org.sonatype.nexus.datastore.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.script.ScriptCleanupHandler;

/**
 * Stub DataStore implementation of {@link ScriptCleanupHandler}.
 *
 * @since 3.24
 */
@Named("datastore")
@Singleton
public class ScriptCleanupHandlerImpl
    extends ComponentSupport
    implements ScriptCleanupHandler
{
  @Override
  public void cleanup(final String context) {
    log.debug("Currently does nothing");
  }
}
