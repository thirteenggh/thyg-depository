package org.sonatype.nexus.orient.testsupport.internal;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.DatabaseManager;
import org.sonatype.nexus.orient.DatabaseManagerSupport;

/**
 * In-memory {@link DatabaseManager} implementation.
 *
 * @since 3.1
 */
@Named("memory")
@Singleton
@Priority(Integer.MIN_VALUE)
public class MemoryDatabaseManager
    extends DatabaseManagerSupport
{
  @Override
  protected String connectionUri(final String name) {
    return "memory:" + name;
  }
}
