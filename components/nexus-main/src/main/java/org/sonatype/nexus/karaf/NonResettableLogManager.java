package org.sonatype.nexus.karaf;

import java.util.logging.LogManager;

/**
 * Custom JUL log manager that ignores {@link #reset()} requests.
 * 
 * @since 3.2.1
 */
public class NonResettableLogManager
    extends LogManager
{
  @Override
  public void reset() {
    // overridden to be a noop
    // reset() is called on several occasions where its standard behavior has undesired effects:
    // - shortly after pax-logging has configured Logback, thereby undoing the efforts of LevelChangePropagator
    // - during JVM shutdown, thereby disabling shutdown logging from components using JUL 
  }
}
