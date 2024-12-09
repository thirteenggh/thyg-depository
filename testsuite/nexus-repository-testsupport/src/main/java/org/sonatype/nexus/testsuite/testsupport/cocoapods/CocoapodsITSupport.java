package org.sonatype.nexus.testsuite.testsupport.cocoapods;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.testsuite.testsupport.RepositoryITSupport;

public class CocoapodsITSupport
    extends RepositoryITSupport
{
  protected static final String COCOAPODS_ROOT_PATH = "/";

  protected static final String POD_CONTENT_TYPE = "application/x-gzip";

  protected static final String GITHUB_API_URL = "http://localhost";

  protected static final int REMOTE_PORT_GITHUB = 57575;

  protected static final int REMOTE_PORT_HTTP = 57576;

  protected static final String GITHUB_API_HOST = "localhost:" + REMOTE_PORT_GITHUB;

  protected static final String NEXUS_PROPERTIES_FILE = "etc/nexus-default.properties";

  protected static final String POD_FILENAME = "test_pod-1.0.0.tar.gz";

  protected static final String SPEC_FILENAME = "test_pod.podspec.json";

  protected static final String INVALID_SPEC_FILENAME = "invalid_test_pod.podspec.json";

  protected static final String POD_GITHUB_API_PATH = "repos/test_vendor/test_pod/tarball/1.0.0";

  protected static final String POD_REMOTE_HTTP_PATH = "some/any/1.0.0.tar.gz";

  protected static final String POD_NAME = "test_pod";

  protected static final String POD_VERSION = "1.0.0";

  protected static final String POD_GITHUB_PATH = "pods/"
      + POD_NAME
      + "/"
      + POD_VERSION
      + "/"
      + POD_VERSION
      + ".tar.gz";

  protected static final String POD_HTTP_PATH = "pods/"
      + POD_NAME
      + "/"
      + POD_VERSION
      + "/1.0.0.tar.gz";

  protected static final String SPEC_PATH = "Specs/0/0/b/test_pod/1.0.0/";

  protected static final String NESTED_PROXY_REPO_NAME = "nested-cocoapods-proxy";

  protected static final String PROXY_REPO_NAME = "cocoapods-proxy";

  protected Repository createCocoapodsProxyRepository(
      final String name,
      final String remoteUrl)
  {
    return repos.createCocoapodsProxy(name, remoteUrl);
  }
}
