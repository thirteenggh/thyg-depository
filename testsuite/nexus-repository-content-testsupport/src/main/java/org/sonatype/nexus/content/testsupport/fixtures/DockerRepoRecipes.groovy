package org.sonatype.nexus.content.testsupport.fixtures

import javax.annotation.Nonnull
import javax.annotation.Nullable

import org.sonatype.nexus.blobstore.api.BlobStoreManager
import org.sonatype.nexus.common.entity.EntityId
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.config.Configuration
import org.sonatype.nexus.repository.config.WritePolicy

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
trait DockerRepoRecipes
    extends ConfigurationRecipes
{
  // TODO: Docker group

  @Nonnull
  Repository createDockerHosted(final String repoName,
                                @Nullable int httpPort,
                                @Nullable int httpsPort,
                                final boolean v1Enabled = true,
                                final WritePolicy writePolicy = WritePolicy.ALLOW,
                                final boolean latestPolicy = false
  )
  {
    Configuration configuration =
        createHosted(repoName, 'docker-hosted', writePolicy, true, BlobStoreManager.DEFAULT_BLOBSTORE_NAME,
            latestPolicy)
    configuration.attributes.docker = configureDockerAttributes(httpPort, httpsPort, v1Enabled)
    createRepository(configuration)
  }

  @Nonnull
  @CompileDynamic
  Repository createDockerProxy(final String name,
                               final String remoteUrl,
                               final String indexType,
                               @Nullable final String indexUrl,
                               @Nullable int httpPort,
                               @Nullable int httpsPort,
                               final boolean v1Enabled = true,
                               final boolean strictContentTypeValidation = true,
                               final boolean cacheForeignLayers = false,
                               final List<String> whitelist = []
  )
  {
    Configuration configuration = createProxy(name, 'docker-proxy', remoteUrl, strictContentTypeValidation)
    configuration.attributes.docker = configureDockerAttributes(httpPort, httpsPort, v1Enabled)
    configuration.attributes.dockerProxy = [
        indexType               : indexType,
        indexUrl                : indexUrl,
        cacheForeignLayers      : cacheForeignLayers,
        foreignLayerUrlWhitelist: whitelist
    ]
    configuration.attributes.httpclient.connection.useTrustStore = true
    createRepository(configuration)
  }


  @Nonnull
  @CompileDynamic
  Repository createDockerProxy(final String name,
                               final String remoteUrl,
                               final String indexType,
                               final EntityId routingRuleId,
                               @Nullable final String indexUrl,
                               @Nullable int httpPort,
                               @Nullable int httpsPort,
                               final boolean v1Enabled = true,
                               final boolean strictContentTypeValidation = true
  )
  {
    Configuration configuration = createProxy(name, 'docker-proxy', remoteUrl, strictContentTypeValidation)
    configuration.attributes.docker = configureDockerAttributes(httpPort, httpsPort, v1Enabled)
    configuration.attributes.dockerProxy = [
        indexType: indexType,
        indexUrl : indexUrl
    ]
    configuration.attributes.httpclient.connection.useTrustStore = true
    configuration.routingRuleId = routingRuleId
    createRepository(configuration)
  }

  @Nonnull
  @CompileDynamic
  Repository createDockerProxy(final String name,
                               final String remoteUrl,
                               final String indexType,
                               @Nullable final String indexUrl,
                               @Nullable int httpPort,
                               @Nullable int httpsPort,
                               final String username,
                               final String password,
                               final boolean v1Enabled = true,
                               final boolean strictContentTypeValidation = true
  )
  {
    Configuration configuration = createProxy(name, 'docker-proxy', remoteUrl, strictContentTypeValidation)
    configuration.attributes.docker = configureDockerAttributes(httpPort, httpsPort, v1Enabled)
    configuration.attributes.dockerProxy = [
        indexType: indexType,
        indexUrl : indexUrl
    ]
    configuration.attributes.httpclient.connection.useTrustStore = true
    configuration.attributes.httpclient.authentication = [
        type    : 'username',
        username: username,
        password: password
    ]
    createRepository(configuration)
  }

  private Map configureDockerAttributes(int httpPort, int httpsPort, boolean v1Enabled) {
    def docker = [:]
    if (httpPort) {
      docker.httpPort = httpPort
    }
    if (httpsPort) {
      docker.httpsPort = httpsPort
    }
    docker.v1Enabled = v1Enabled
    return docker
  }

  abstract Repository createRepository(final Configuration configuration)


}
