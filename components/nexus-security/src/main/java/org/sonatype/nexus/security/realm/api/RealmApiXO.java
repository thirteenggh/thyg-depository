package org.sonatype.nexus.security.realm.api;

import org.sonatype.nexus.security.realm.SecurityRealm;

/**
 * REST API model of a security realm
 * 
 * @since 3.20
 */
public class RealmApiXO
{
  private String id;

  private String name;

  public RealmApiXO(final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public static RealmApiXO from(final SecurityRealm securityRealm) {
    return new RealmApiXO(securityRealm.getId(), securityRealm.getName());
  }
}
