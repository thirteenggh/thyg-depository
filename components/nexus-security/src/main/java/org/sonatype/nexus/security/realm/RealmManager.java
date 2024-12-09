package org.sonatype.nexus.security.realm;

import java.util.List;

import org.sonatype.goodies.lifecycle.Lifecycle;

/**
 * Realm manager.
 *
 * @since 3.0
 */
public interface RealmManager
  extends Lifecycle
{
  /**
   * Returns a new realm configuration entity
   *
   * @since 3.20
   */
  RealmConfiguration newEntity();

  /**
   * Returns copy of current realm configuration.
   */
  RealmConfiguration getConfiguration();

  /**
   * Installs new realm configuration.
   */
  void setConfiguration(RealmConfiguration configuration);

  /**
   * Check if given realm-name is enabled.
   */
  boolean isRealmEnabled(String realmName);

  /**
   * Helper to enable or disable given realm-name.
   *
   * @see #enableRealm
   * @see #disableRealm
   */
  void enableRealm(String realmName, boolean enable);

  /**
   * Enable given realm-name, if not already enabled.
   */
  void enableRealm(String realmName);

  /**
   * Disable given realm-name, if not already disabled.
   */
  void disableRealm(String realmName);

  /**
   * Get the list of known available realms.
   *
   * @since 3.20
   */
  List<SecurityRealm> getAvailableRealms();
}
