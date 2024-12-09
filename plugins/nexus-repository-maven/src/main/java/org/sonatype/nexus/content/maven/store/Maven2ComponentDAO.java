
package org.sonatype.nexus.content.maven.store;

import org.sonatype.nexus.repository.content.store.ComponentDAO;

/**
 * @since 3.25
 */
public interface Maven2ComponentDAO
    extends ComponentDAO
{
  /**
   * Adds base_version column. See {@see Maven2ComponentDAO.xml}
   */
  void extendSchema();

  /**
   * Updates the maven base_version of the given component in the content data store.
   *
   * @param component the component to update
   */
  void updateBaseVersion(Maven2ComponentData component);
}
