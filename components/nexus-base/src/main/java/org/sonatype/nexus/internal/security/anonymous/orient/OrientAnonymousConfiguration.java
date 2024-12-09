package org.sonatype.nexus.internal.security.anonymous.orient;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

/**
 * An OrientDB backed implementation of {@link AnonymousConfiguration}
 *
 * @since 3.20
 */
public class OrientAnonymousConfiguration
    extends AbstractEntity
    implements AnonymousConfiguration, Cloneable
{
  private boolean enabled;

  private String userId;

  private String realmName;

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }

  // TODO: Sort out nullability of user-id and realm-name

  @Override
  public String getUserId() {
    return userId;
  }

  @Override
  public void setUserId(final String userId) {
    this.userId = userId;
  }

  @Override
  public String getRealmName() {
    return realmName;
  }

  @Override
  public void setRealmName(final String realmName) {
    this.realmName = realmName;
  }

  @Override
  public AnonymousConfiguration copy() {
    try {
      return (OrientAnonymousConfiguration) clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" + "enabled=" + enabled + ", userId='" + userId + '\'' + ", realmName='"
        + realmName + '\'' + '}';
  }
}
