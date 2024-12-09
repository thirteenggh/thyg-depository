package org.sonatype.nexus.repository.storage;

import java.util.List;

import org.sonatype.nexus.repository.Repository;

/**
 * Finds components that match a given set of parameters.
 *
 * @since 3.14
 */
public interface ComponentFinder
{
  List<Component> findMatchingComponents(final Repository repository,
                                         final String componentId,
                                         final String componentGroup,
                                         final String componentName,
                                         final String componentVersion);
}
