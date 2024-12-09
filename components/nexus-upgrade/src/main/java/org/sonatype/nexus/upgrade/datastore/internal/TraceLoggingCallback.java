package org.sonatype.nexus.upgrade.datastore.internal;

import org.flywaydb.core.api.callback.BaseCallback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Flyway callback which trace logs the actions being performed.
 *
 * @since 3.29
 */
public class TraceLoggingCallback
    extends BaseCallback
{
  private static final Logger log = LoggerFactory.getLogger(TraceLoggingCallback.class);

  @Override
  public void handle(final Event event, final Context context) {
    log.trace("{} Migration Description: \"{}\" State:\"{}\"", event.getId(),
        context.getMigrationInfo().getDescription(), context.getMigrationInfo().getState());
  }
}
