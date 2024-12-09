package org.sonatype.nexus.repository.cocoapods.upgrade;

/**
 * Holds information about the 'cocoapods' model defined by this plugin.
 *
 * This model is stored in the 'component' database as attributes in the generic 'component' model.
 * Upgrades should depend on the 'component' model and version at the time the upgrade was written.
 *
 * @since 3.27
 */
public interface CocoapodsModel
{
  String NAME = "cocoapods";
}
