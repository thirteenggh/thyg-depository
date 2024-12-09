package org.sonatype.nexus.testsuite.testsupport.raw;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.orient.raw.RawContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.testsuite.helpers.ComponentAssetTestHelper;
import org.sonatype.nexus.transaction.UnitOfWork;

import static org.junit.Assert.assertTrue;

@FeatureFlag(name = "nexus.orient.enabled")
@Named
@Priority(Integer.MAX_VALUE)
@Singleton
public class OrientRawTestHelper
    implements RawTestHelper
{
  @Inject
  ComponentAssetTestHelper componentAssetTestHelper;

  @Override
  public Content read(final Repository repository, final String path)
      throws IOException
  {
    RawContentFacet rawFacet = repository.facet(RawContentFacet.class);
    UnitOfWork.begin(repository.facet(StorageFacet.class).txSupplier());
    try {
      return rawFacet.get(path);
    }
    finally {
      UnitOfWork.end();
    }
  }

  @Override
  public void assertRawComponent(final Repository repository, final String path, final String group) {
    assertTrue(componentAssetTestHelper.assetWithComponentExists(repository, path, group, path));
  }

  @Override
  public EntityId createAsset(
      final Repository repository, final String componentName, final String componentGroup, final String assetName)
  {
    UnitOfWork.begin(repository.facet(StorageFacet.class).txSupplier());
    try {
      return repository.facet(RawContentFacet.class).getOrCreateAsset(repository, componentName, componentGroup, assetName).componentId();
    }
    finally {
      UnitOfWork.end();
    }
  }
}
