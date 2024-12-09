package org.sonatype.nexus.security.role;

/**
 * Emitted when a {@link Role} has been updated.
 *
 * @since 3.1
 */
public class RoleUpdatedEvent
  extends RoleEvent
{
  public RoleUpdatedEvent(final Role role) {
    super(role);
  }
}
