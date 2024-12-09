package org.sonatype.nexus.orient;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Holds the names of commonly used databases.
 * 
 * @since 3.1
 */
public class DatabaseInstanceNames
{
  /**
   * Name of the database storing component metadata.
   */
  public static final String COMPONENT = "component";

  /**
   * Name of the database storing system configuration.
   */
  public static final String CONFIG = "config";

  /**
   * Name of the database storing security configuration.
   */
  public static final String SECURITY = "security";

  public static final Set<String> DATABASE_NAMES = ImmutableSet.of(
      CONFIG, COMPONENT, SECURITY);

  private DatabaseInstanceNames() {
    // no construction
  }
}
