package org.sonatype.nexus.repository.storage;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.DetachedEntityId;
import org.sonatype.nexus.repository.Repository;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * @since 3.14
 */
@Singleton
@Named
public class DefaultComponentFinder
    implements ComponentFinder
{
  public static final String DEFAULT_COMPONENT_FINDER_KEY = "default";

  @Override
  public List<Component> findMatchingComponents(final Repository repository,
                                                final String componentId,
                                                final String componentGroup,
                                                final String componentName,
                                                final String componentVersion)
  {
    if (null == repository || null == componentId || componentId.isEmpty()) {
      return emptyList();
    } else {
      try (StorageTx storageTx = repository.facet(StorageFacet.class).txSupplier().get()) {
        storageTx.begin();
        Component component = storageTx.findComponent(new DetachedEntityId(componentId));
        if (component != null) {
          return singletonList(component);
        }
        else {
          return emptyList();
        }
      }
    }
  }
}
