package org.sonatype.nexus.orient;

import com.orientechnologies.orient.server.config.OServerConfiguration;

/**
 * Allows extensible customization of the Orient configuration.
 *
 * @since 3.6
 */
public interface OrientConfigCustomizer
{
  void apply(OServerConfiguration config);
}
