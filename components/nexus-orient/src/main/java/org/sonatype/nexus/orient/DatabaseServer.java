package org.sonatype.nexus.orient;

import java.util.List;

import org.sonatype.goodies.lifecycle.Lifecycle;

/**
 * Database server.
 *
 * @since 3.0
 */
public interface DatabaseServer
  extends Lifecycle
{
  /**
   * Lists the names of the databases hosted by the server
   *
   * @return names of databases
   * @since 3.2
   */
  List<String> databases();
}
