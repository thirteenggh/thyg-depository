package org.sonatype.nexus.testsuite.testsupport.raw;

import java.io.IOException;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Content;

public interface RawTestHelper
{
  Content read(Repository repository, String path) throws IOException;

  void assertRawComponent(Repository repository, String path, String group);

  EntityId createAsset(Repository repository, String componentName, String componentGroup, String assetName);
}
