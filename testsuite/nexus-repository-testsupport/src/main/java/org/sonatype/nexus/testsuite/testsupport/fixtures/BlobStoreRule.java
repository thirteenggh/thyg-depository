package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Provider;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.blobstore.file.FileBlobStore;
import org.sonatype.nexus.blobstore.group.BlobStoreGroup;
import org.sonatype.nexus.blobstore.group.internal.WriteToFirstMemberFillPolicy;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.sonatype.nexus.blobstore.file.FileBlobStore.PATH_KEY;

/**
 * @since 3.20
 */
public class BlobStoreRule
    extends ExternalResource
{
  private static final Logger log = LoggerFactory.getLogger(BlobStoreRule.class);

  private final Provider<BlobStoreManager> blobStoreManagerProvider;

  private final List<String> blobStoreNames = new ArrayList<>();

  private final List<String> blobStoreGroupNames = new ArrayList<>();

  public BlobStoreRule(final Provider<BlobStoreManager> blobStoreManagerProvider) {
    this.blobStoreManagerProvider = blobStoreManagerProvider;
  }

  public BlobStore createFile(String name) throws Exception {
    BlobStoreConfiguration config = blobStoreManagerProvider.get().newConfiguration();
    config.setName(name);
    config.setType(FileBlobStore.TYPE);
    config.attributes(FileBlobStore.CONFIG_KEY).set(PATH_KEY, name);
    BlobStore blobStore = blobStoreManagerProvider.get().create(config);
    blobStoreNames.add(name);
    return blobStore;
  }

  public BlobStore createGroup(String name, String... members) throws Exception {
    BlobStoreConfiguration config = blobStoreManagerProvider.get().newConfiguration();
    config.setName(name);
    config.setType(BlobStoreGroup.TYPE);
    config.attributes(BlobStoreGroup.CONFIG_KEY).set(BlobStoreGroup.MEMBERS_KEY, asList(members));
    config.attributes(BlobStoreGroup.CONFIG_KEY).set(BlobStoreGroup.FILL_POLICY_KEY, WriteToFirstMemberFillPolicy.TYPE);
    BlobStore blobStore = blobStoreManagerProvider.get().create(config);
    blobStoreGroupNames.add(name);
    return blobStore;
  }

  public void cleanBlobstore(String name) {
    BlobStore blobStore = blobStoreManagerProvider.get().get(name);
    blobStore.getBlobIdStream().forEach(blobId -> {
      blobStore.deleteHard(blobId);
    });
  }

  @Override
  protected void after() {
    blobStoreGroupNames.forEach(blobStoreGroupName -> {
      try {
        blobStoreManagerProvider.get().delete(blobStoreGroupName);
      }
      catch (Exception e) {
        log.error("Failed to remove blobstore group {}", blobStoreGroupName, e);
      }
    });
    blobStoreNames.forEach(blobStoreName -> {
      try {
        blobStoreManagerProvider.get().delete(blobStoreName);
      }
      catch (Exception e) {
        log.error("Failed to remove blobstore {}", blobStoreName, e);
      }
    });
  }
}
