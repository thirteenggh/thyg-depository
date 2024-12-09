package org.sonatype.nexus.repository.config;

import java.util.List;

import org.sonatype.goodies.lifecycle.Lifecycle;

/**
 * {@link Configuration} store.
 *
 * @since 3.0
 */
public interface ConfigurationStore
  extends Lifecycle
{
  List<Configuration> list();

  void create(Configuration configuration);

  void update(Configuration configuration);

  void delete(Configuration configuration);

  /**
   * Create a new {@link Configuration} instance.
   *
   * @since 3.21
   */
  Configuration newConfiguration();
}
