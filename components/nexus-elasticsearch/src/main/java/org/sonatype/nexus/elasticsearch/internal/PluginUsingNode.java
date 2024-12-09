package org.sonatype.nexus.elasticsearch.internal;

import java.util.Collection;

import org.elasticsearch.Version;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.internal.InternalSettingsPreparer;
import org.elasticsearch.plugins.Plugin;

/**
 * Custom {@link org.elasticsearch.node.Node} implementation to allow {@link Plugin} classes to be passed into the
 * constructor.
 *
 * @since 3.1
 */
public class PluginUsingNode
    extends Node
{
  public PluginUsingNode(final Settings preparedSettings, Collection<Class<? extends Plugin>> plugins) {
    super(InternalSettingsPreparer.prepareEnvironment(preparedSettings, null), Version.CURRENT, plugins);
  }
}

