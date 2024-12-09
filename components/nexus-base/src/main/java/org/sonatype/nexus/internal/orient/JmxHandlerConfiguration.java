package org.sonatype.nexus.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import com.orientechnologies.orient.server.config.OServerHandlerConfiguration;
import com.orientechnologies.orient.server.config.OServerParameterConfiguration;
import com.orientechnologies.orient.server.handler.OJMXPlugin;

/**
 * Enables the JMX plugin on the OrientDB server.
 *
 * @since 3.0
 */
@Named
@Singleton
public class JmxHandlerConfiguration
    extends OServerHandlerConfiguration
{
  public JmxHandlerConfiguration() {
    clazz = OJMXPlugin.class.getName();
    parameters = new OServerParameterConfiguration[] {
        new OServerParameterConfiguration("enabled", "true"),
        new OServerParameterConfiguration("profilerManaged", "true")
    };
  }
}
