package org.sonatype.nexus.testsuite.testsupport.raw;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.FeatureFlag;
import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.content.raw.RawContentFacet;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.store.InternalIds;
import org.sonatype.nexus.repository.view.Content;
import org.sonatype.nexus.repository.view.payloads.StringPayload;
import org.sonatype.nexus.testsuite.helpers.ComponentAssetTestHelper;

import org.apache.commons.lang.StringUtils;

import static org.junit.Assert.assertTrue;

@FeatureFlag(name = "nexus.datastore.enabled")
@Named
@Singleton
public class DatastoreRawTestHelper
    implements RawTestHelper
{
  @Inject
  ComponentAssetTestHelper componentAssetTestHelper;

  @Override
  public Content read(final Repository repository, final String path)
      throws IOException
  {
    return repository.facet(RawContentFacet.class).get(adjust(path)).orElse(null);
  }

  @Override
  public void assertRawComponent(final Repository repository, final String path, final String group) {
    assertTrue(componentAssetTestHelper.assetWithComponentExists(repository, adjust(path), group, adjust(path)));
  }

  @Override
  public EntityId createAsset(
      final Repository repository, final String componentName, final String componentGroup, final String assetName)
  {
    try {
      repository.facet(RawContentFacet.class).put(getGroupAndAsset(componentGroup, assetName), new StringPayload("Test", "text/plain"));
      Optional<FluentComponent> fluentComponent = repository
          .facet(RawContentFacet.class)
          .components()
          .name("/" + assetName)
          .namespace("/" + componentGroup)
          .find();

      assertTrue(fluentComponent.isPresent());
      return InternalIds.toExternalId(InternalIds.internalComponentId(fluentComponent.get()));
    } catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  private String getGroupAndAsset(String group, String assetName){
    return getGroup(group) + "/" + assetName;
  }

  private String getGroup(String group) {
    if (StringUtils.isEmpty(group)) {
      return "";
    }
    return "/" + group;
  }

  private String adjust(final String path) {
    return (path.startsWith("/") ? "" : "/") + path;
  }
}
