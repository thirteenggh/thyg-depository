package org.sonatype.nexus.internal.security.model.orient;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.security.config.CUser;

/**
 * An OrientDB backed persistent user.
 *
 * @since 3.0
 */
public class OrientCUser
    extends AbstractEntity
    implements CUser
{
  private String email;

  private String firstName;

  private String id;

  private String lastName;

  private String password;

  private String status;

  private int version;

  OrientCUser() {
    //
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getStatus() {
    return this.status;
  }

  @Override
  public int getVersion() {
    return version;
  }

  @Override
  public boolean isActive() {
    return STATUS_ACTIVE.equals(status) || STATUS_CHANGE_PASSWORD.equals(status);
  }

  @Override
  public void setEmail(final String email) {
    this.email = email;
  }

  @Override
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  @Override
  public void setId(final String id) {
    this.id = id;
  }

  @Override
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public void setPassword(final String password) {
    this.password = password;
  }

  @Override
  public void setStatus(final String status) {
    this.status = status;
  }

  @Override
  public void setVersion(final int version) {
    this.version = version;
  }

  @Override
  public OrientCUser clone() {
    try {
      return (OrientCUser) super.clone();
    }
    catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "id='" + id + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", password='" + Strings2.mask(password) + '\'' +
        ", status='" + status + '\'' +
        ", email='" + email + '\'' +
        ", version='" + version + '\'' +
        '}';
  }
}
