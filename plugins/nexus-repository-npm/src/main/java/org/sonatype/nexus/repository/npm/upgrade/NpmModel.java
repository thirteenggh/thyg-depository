package org.sonatype.nexus.repository.npm.upgrade;

/**
 * Holds information about the 'npm' model defined by this plugin.
 *
 * This model is stored in the 'component' database as attributes in the generic 'component' model.
 * While previous upgrades used the generic 'component' model name, future upgrades should use this
 * more specific model.
 *
 * Upgrades should depend on the 'component' model and version at the time the upgrade was written.
 *
 * @since 3.13
 */
public interface NpmModel
{
  String NAME = "npm";
}
