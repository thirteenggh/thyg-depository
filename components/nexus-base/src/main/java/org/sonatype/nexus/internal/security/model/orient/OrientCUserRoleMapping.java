package org.sonatype.nexus.internal.security.model.orient;

import java.util.Set;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.security.config.CUserRoleMapping;

import static com.google.common.collect.Sets.newHashSet;

/**
 * An OrientDB backed persistent user-role mapping.
 *
 * @since 3.0
 */
public class OrientCUserRoleMapping
    extends AbstractEntity
    implements CUserRoleMapping
{
  private Set<String> roles;

  private String source;

  private String userId;

  private String version;

  @Override
  public void addRole(final String string) {
    getRoles().add(string);
  }

  @Override
  public OrientCUserRoleMapping clone() {
    try {
      OrientCUserRoleMapping copy = (OrientCUserRoleMapping) super.clone();

      if (this.roles != null) {
        copy.roles = newHashSet(this.roles);
      }

      return copy;
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Set<String> getRoles() {
    if (this.roles == null) {
      this.roles = newHashSet();
    }
    return this.roles;
  }

  @Override
  public String getSource() {
    return this.source;
  }

  @Override
  public String getUserId() {
    return this.userId;
  }

  @Override
  public String getVersion() {
    return version;
  }

  @Override
  public void removeRole(final String string) {
    getRoles().remove(string);
  }

  @Override
  public void setRoles(final Set<String> roles) {
    this.roles = roles;
  }

  @Override
  public void setSource(final String source) {
    this.source = source;
  }

  @Override
  public void setUserId(final String userId) {
    this.userId = userId;
  }

  @Override
  public void setVersion(final String version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "userId='" + userId + '\'' +
        ", source='" + source + '\'' +
        ", roles=" + roles +
        ", version='" + version + '\'' +
        '}';
  }
}
