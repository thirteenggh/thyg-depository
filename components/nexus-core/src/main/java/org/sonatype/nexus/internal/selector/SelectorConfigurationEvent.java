package org.sonatype.nexus.internal.selector;

import org.sonatype.nexus.selector.SelectorConfiguration;

/**
 * {@link SelectorConfiguration} event.
 *
 * @since 3.1
 */
public interface SelectorConfigurationEvent
{
  boolean isLocal();

  SelectorConfiguration getSelectorConfiguration();
}
