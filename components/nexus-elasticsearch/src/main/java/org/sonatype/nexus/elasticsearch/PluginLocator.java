package org.sonatype.nexus.elasticsearch;

import org.elasticsearch.plugins.Plugin;

/**
 * Provides a custom mechanism to locate {@link Plugin} implementations that have been deployed.
 *
 * @since 3.1
 */
public interface PluginLocator
{
  Class<? extends Plugin> pluginClass();
}
