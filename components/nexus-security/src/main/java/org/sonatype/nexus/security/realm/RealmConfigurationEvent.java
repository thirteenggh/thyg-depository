package org.sonatype.nexus.security.realm;

/**
 * Event for {@link RealmConfiguration} entity.
 *
 * @since 3.1
 */
public interface RealmConfigurationEvent
{
  boolean isLocal();

  RealmConfiguration getConfiguration();
}
