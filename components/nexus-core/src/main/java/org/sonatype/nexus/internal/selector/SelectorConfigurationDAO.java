package org.sonatype.nexus.internal.selector;

import org.sonatype.nexus.datastore.api.NamedDataAccess;

/**
 * {@link SelectorConfigurationData} access.
 *
 * @since 3.21
 */
public interface SelectorConfigurationDAO
    extends NamedDataAccess<SelectorConfigurationData>
{
  // no additional behaviour
}
