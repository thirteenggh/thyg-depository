package org.sonatype.nexus.testsuite.testsupport.pypi;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.maintenance.ContentMaintenanceFacet;
import org.sonatype.nexus.repository.pypi.datastore.PypiContentFacet;

@Named
@Singleton
public class PyPiDataStoreTestHelper
    implements PyPiTestHelper
{
  @Override
  public void deleteComponent(final Repository repository, final String name, final String version)
  {
    PypiContentFacet contentFacet = repository.facet(PypiContentFacet.class);
    ContentMaintenanceFacet maintenanceFacet = repository.facet(ContentMaintenanceFacet.class);
    contentFacet.components().name(name).version(version).find().map(maintenanceFacet::deleteComponent);
  }

  @Override
  public boolean isRootIndexExist(final Repository repository) {
    return repository.facet(PypiContentFacet.class).getAsset("simple/").isPresent();
  }
}
