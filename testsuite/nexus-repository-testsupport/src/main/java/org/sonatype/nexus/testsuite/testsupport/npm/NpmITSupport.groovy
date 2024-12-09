package org.sonatype.nexus.testsuite.testsupport.npm

import javax.inject.Inject

import org.sonatype.nexus.common.collect.NestedAttributesMap
import org.sonatype.nexus.common.log.LogManager
import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.npm.security.NpmToken
import org.sonatype.nexus.testsuite.testsupport.RepositoryITSupport

import com.google.common.collect.ImmutableSet
import groovy.json.JsonSlurper
import org.apache.http.HttpResponse
import org.apache.http.util.EntityUtils
import org.junit.experimental.categories.Category

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.sonatype.nexus.repository.http.HttpStatus.CREATED
import static org.sonatype.nexus.repository.http.HttpStatus.NOT_FOUND

/**
 * Support for npm ITs.
 */
@Category(NpmTestGroup)
class NpmITSupport
    extends RepositoryITSupport
{
  @Inject
  protected LogManager logManager

  NpmITSupport() {
    testData.addDirectory(resolveBaseFile("target/it-resources/npm"))
  }

  void enableNpmRealm() {
    def config = realmManager.configuration
    if (!config.realmNames.contains(NpmToken.NAME)) {
      config.realmNames.add(NpmToken.NAME)
      realmManager.configuration = config
    }
  }

  Repository createNpmHostedRepository(String name, String writePolicy = 'ALLOW_ONCE') {
    return repos.createNpmHosted(name, writePolicy)
  }

  Repository createNpmHostedRepositoryWithBlobstore(String name, String blobStoreName) {
    return repos.createNpmHosted(name, 'ALLOW_ONCE', blobStoreName)
  }

  Repository createNpmGroupRepository(String repositoryName, String... members) {
    return repos.createNpmGroup(repositoryName, members)
  }

  Repository createNpmGroupRepository(String repositoryName, Repository writeable, String... members) {
    return repos.createNpmGroup(repositoryName, writeable, members)
  }

  Repository createNpmProxyRepository(final String name, final String remoteUrl,
                                      final Map<String, Object> authentication = [:])
  {
    return repos.createNpmProxy(name, remoteUrl, authentication)
  }

  protected def checkLogin(NpmClient client, String username, String password, int expectedStatus) {
    return client.login(username, password, "foo@bar.baz").withCloseable { response ->
      log.debug("deserialized response: {}", response)
      return response.statusLine.statusCode == expectedStatus
    }
  }

  protected static def login(NpmClient client, String username, String password) {
    return client.login(username, password, "foo@bar.baz").withCloseable { response ->
      if (response.statusLine.statusCode == CREATED) {
        return new JsonSlurper().parse(response.entity.content).token
      }
      else {
        return null
      }
    }
  }

  protected NestedAttributesMap fetchMetadataAndVerifyVersions(final NpmClient client,
                                                               final String... versions)
  {
    return fetchMetadataAndVerifyVersions('foo', client, versions)
  }

  protected NestedAttributesMap fetchMetadataAndVerifyVersions(final String packageName,
                                                               final NpmClient client,
                                                               final String... versions)
  {
    NestedAttributesMap fetchedPackageRoot = client.fetch(packageName)

    assert !fetchedPackageRoot.empty
    assert fetchedPackageRoot['name'] == packageName
    assert fetchedPackageRoot['versions'].size() == versions.length

    NestedAttributesMap versionsMap = fetchedPackageRoot.child('versions')
    assertThat(versionsMap.backing().keySet(), equalTo(ImmutableSet.copyOf(versions)))
    fetchedPackageRoot
  }

  protected verifyNotFound(final NpmClient client, final String packageName) {
    HttpResponse response = client.getResponseForPackage(packageName)
    try {
      assert response.getStatusLine().getStatusCode() == NOT_FOUND
    }
    finally {
      EntityUtils.consumeQuietly(response.entity)
    }
  }
}
