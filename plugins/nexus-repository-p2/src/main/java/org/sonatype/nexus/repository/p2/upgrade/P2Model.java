package org.sonatype.nexus.repository.p2.upgrade;

/**
 * Holds information about the 'p2' model defined by this plugin.
 *
 * This model is stored in the 'component' database as attributes in the generic 'component' model.
 * Upgrades should depend on the 'component' model and version at the time the upgrade was written.
 *
 * @since 3.28
 */
public interface P2Model
{
  String NAME = "p2";
}
