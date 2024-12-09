package org.sonatype.nexus.repository.config;

/**
 * Write policy.
 *
 * @since 3.0
 */
public enum WritePolicy
{
  /**
   * Asset can be linked with a blob.
   * Asset can be re-linked with another blob.
   * Asset can be unlinked from a blob (blob can be deleted).
   */
  ALLOW,
  /**
   * Asset can be linked with a blob.
   * Asset cannot be re-linked with another blob.
   * Asset can be unlinked from a blob (blob can be deleted).
   */
  ALLOW_ONCE,
  /**
   * Asset cannot be linked with a blob.
   * Asset cannot be re-linked with another blob.
   * Asset cannot be unlinked from a blob.
   */
  DENY;

  /**
   * Returns {@code true} if Create allowed with this policy.
   */
  public boolean checkCreateAllowed() {
    return this != DENY;
  }

  /**
   * Returns {@code true} if Update allowed with this policy.
   */
  public boolean checkUpdateAllowed() {
    return this == ALLOW;
  }

  /**
   * Returns {@code true} if Delete allowed with this policy.
   */
  public boolean checkDeleteAllowed() {
    return this != DENY;
  }
}
