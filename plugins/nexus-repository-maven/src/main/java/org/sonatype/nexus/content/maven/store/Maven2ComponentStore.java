
package org.sonatype.nexus.content.maven.store;

import javax.inject.Inject;

import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.repository.content.store.ComponentStore;
import org.sonatype.nexus.transaction.Transactional;

import com.google.inject.assistedinject.Assisted;

/**
 * @since 3.29
 */
public class Maven2ComponentStore
    extends ComponentStore<Maven2ComponentDAO>
{
  @Inject
  public Maven2ComponentStore(final DataSessionSupplier sessionSupplier,
                              @Assisted final String storeName)
  {
    super(sessionSupplier, storeName, Maven2ComponentDAO.class);
  }

  /**
   * Updates the maven base_version of the given component in the content data store.
   *
   * @param component the component to update
   */
  @Transactional
  public void updateBaseVersion(final Maven2ComponentData component)
  {
    dao().updateBaseVersion(component);
  }
}
