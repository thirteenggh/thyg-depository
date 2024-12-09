package com.sonatype.nexus.ssl.plugin.upgrade;

/**
 * Holds information about the 'keystore' model defined by this plugin.
 *
 * This model is stored in the 'config' database under its own schema. While previous upgrades
 * used the generic 'config' model name, future upgrades should use this more specific model.
 *
 * Upgrades should depend on the 'config' model and version at the time the upgrade was written.
 *
 * @since 3.13
 */
public interface KeyStoreModel
{
  String NAME = "keystore";
}
