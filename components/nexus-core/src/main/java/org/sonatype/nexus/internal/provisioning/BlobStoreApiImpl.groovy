package org.sonatype.nexus.internal.provisioning

import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

import org.sonatype.nexus.BlobStoreApi
import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration
import org.sonatype.nexus.blobstore.api.BlobStoreManager

import static com.google.common.base.Preconditions.checkNotNull

/**
 * @since 3.0
 */
@Named
@Singleton
class BlobStoreApiImpl
    implements BlobStoreApi
{
  @Inject
  BlobStoreManager blobStoreManager

  @Override
  BlobStoreConfiguration createFileBlobStore(final String name, final String path) {
    def attributes = [file: [path: checkNotNull(path)]]
    def config = blobStoreManager.newConfiguration()
    config.name = name
    config.setType('File')
    config.attributes = [file: [path: checkNotNull(path)]]
    return blobStoreManager.create(config).blobStoreConfiguration
  }

  @Override
  BlobStoreConfiguration createBlobStoreGroup(final String name, List<String> memberNames, String fillPolicy) {
    def attributes = [group: [members: memberNames, fillPolicy: fillPolicy]]
    def config = blobStoreManager.newConfiguration()
    config.setName(name)
    config.setType('Group')
    config.setAttributes(attributes)
    return blobStoreManager.create(config).blobStoreConfiguration
  }

  @Override
  BlobStoreConfiguration createS3BlobStore(final String name, final Map<String, String> s3Config) {
    def attributes = [s3: s3Config]
    def config = blobStoreManager.newConfiguration()
    config.setName(name)
    config.setType('S3')
    config.setAttributes(attributes)
    return blobStoreManager.create(config).blobStoreConfiguration
  }
}
