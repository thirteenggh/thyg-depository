package org.sonatype.nexus.repository.rest.cma;

import org.sonatype.nexus.repository.rest.api.ComponentXO;
import org.sonatype.nexus.repository.rest.internal.resources.ComponentsResource;
import org.sonatype.nexus.repository.storage.Component;

/**
 * Extension point for the {@link ComponentsResource} class
 *
 * @since 3.8
 */
public interface ComponentsResourceExtension
{
  /**
   * Update the {@link ComponentXO} with the data in the {@link Component}
   */
  ComponentXO updateComponentXO(ComponentXO componentXO, Component component);
}
