package org.sonatype.nexus.upgrade.internal.orient;

/**
 * Represents a distinct point in the upgrade process.
 * 
 * @since 3.1
 */
public interface UpgradePoint
{
  /**
   * @return {@code true} if the given model and version are satisfied at this point
   */
  boolean satisfies(String model, String version);
}
