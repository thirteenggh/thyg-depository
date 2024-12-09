package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull
import javax.inject.Provider

import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy
import org.sonatype.nexus.repository.manager.RepositoryManager

import groovy.transform.CompileStatic

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkNotNull

/**
 * Common Repository configuration aspects and constants.
 */
@CompileStatic
trait ConfigurationRecipes
{
  abstract Provider<RepositoryManager> getRepositoryManagerProvider()

  /**
   * Create a hosted configuration for the given recipeName.
   */
  @Nonnull
  Configuration createHosted(final String name,
                             final String recipeName,
                             final WritePolicy writePolicy = WritePolicy.ALLOW,
                             final boolean strictContentTypeValidation = true,
                             final String blobStoreName = BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
                             final boolean latestPolicy = false)
  {
    checkNotNull(name)
    checkArgument(recipeName && recipeName.endsWith('-hosted'))

    newConfiguration(
        repositoryName: name,
        recipeName: recipeName,
        online: true,
        attributes: [
            storage: [
                blobStoreName: blobStoreName,
                writePolicy  : writePolicy,
                latestPolicy  : latestPolicy,
                strictContentTypeValidation: strictContentTypeValidation
            ] as Map
        ] as Map
    )
  }

  /**
   * Create a proxy configuration for the given recipeName.
   */
  @Nonnull
  Configuration createProxy(final String name,
                            final String recipeName,
                            final String remoteUrl,
                            final boolean strictContentTypeValidation = true,
                            final String blobStoreName = BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
                            final Map<String, Object> authentication = [:])
  {
    checkNotNull(name)
    checkArgument(recipeName && recipeName.endsWith('-proxy'))

    def attributes = [
        httpclient   : [
            connection: [
                blocked  : false,
                autoBlock: true
            ] as Map<String, Object>
        ] as Map<String, Object>,
        proxy        : [
            remoteUrl     : remoteUrl,
            contentMaxAge : 1440,
            metadataMaxAge: 1440
        ] as Map<String, Object>,
        negativeCache: [
            enabled   : true,
            timeToLive: 1440
        ] as Map<String, Object>,
        storage      : [
            blobStoreName              : blobStoreName,
            strictContentTypeValidation: strictContentTypeValidation
        ] as Map<String, Object>
    ]
    if (!authentication.isEmpty()) {
      attributes.httpclient.authentication = authentication
    }

    newConfiguration(
        repositoryName: name,
        recipeName: recipeName,
        online: true,
        attributes: attributes
    )
  }

  /**
   * Create a group configuration for the given recipeName.
   */
  @Nonnull
  Configuration createGroup(final String name,
                            final String recipeName,
                            final String... members)
  {
    checkNotNull(name)
    checkArgument(recipeName && recipeName.endsWith('-group'))

    newConfiguration(
        repositoryName: name,
        recipeName: recipeName,
        online: true,
        attributes: [
            group  : [
                memberNames: members.toList()
            ] as Map<String, Object>,
            storage: [
                blobStoreName: BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
                strictContentTypeValidation: true
            ] as Map<String, Object>
        ]
    )
  }

  Configuration newConfiguration(final Map map) {
    Configuration config = repositoryManagerProvider.get().newConfiguration()
    config.repositoryName = map.repositoryName
    config.recipeName = map.recipeName
    config.online = map.online
    config.attributes = map.attributes as Map
    return config
  }
}
