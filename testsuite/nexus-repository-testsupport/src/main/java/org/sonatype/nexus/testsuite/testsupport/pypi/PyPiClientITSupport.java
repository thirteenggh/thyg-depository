package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;

import com.sonatype.nexus.docker.testsupport.framework.DockerContainerConfig;
import com.sonatype.nexus.docker.testsupport.pypi.PyPiCommandLineITSupport;

import org.sonatype.nexus.testsuite.testsupport.FormatClientITSupport;
import org.sonatype.nexus.testsuite.testsupport.RepositoryITSupport;
import org.sonatype.nexus.testsuite.testsupport.utility.SearchTestHelper;

import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;

import static com.google.common.io.Files.write;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.io.FileUtils.contentEquals;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.editConfigurationFileExtend;
import static org.sonatype.nexus.common.io.NetworkHelper.findLocalHostAddress;
import static org.sonatype.nexus.repository.http.HttpStatus.OK;
import static org.sonatype.nexus.repository.search.query.RepositoryQueryBuilder.unrestricted;
import static org.sonatype.nexus.testsuite.testsupport.FormatClientSupport.status;

/**
 * Support class for PyPi Format Client ITs.
 *
 * @since 3.13
 */
public abstract class PyPiClientITSupport
    extends FormatClientITSupport
{
  protected PyPiCommandLineITSupport pyPiCli;

  private static final String PYTHON_PACKAGE_VERSION = "version";

  private static final String PYTHON_PACKAGE_NAME = "name";

  private final PyPiClientFactory pyPiClientFactory = new PyPiClientFactory();

  @Inject
  protected SearchTestHelper searchTesthelper;

  /**
   * Default {@link DockerContainerConfig} for testing PyPi. Should at
   * a minimum add a volume for the pip.conf file.
   *
   * @return DockerContainerConfig
   */
  protected abstract DockerContainerConfig createTestConfig() throws Exception;

  @Configuration
  public static Option[] configureNexus() {
    Option[] nexusBase = options(
        RepositoryITSupport.configureNexus(),
        nexusFeature("org.sonatype.nexus.testsuite", "nexus-docker-testsupport"),
        withHttps(resolveBaseFile(format("target/it-resources/ssl/%s.jks", DOCKER_HOST_NAME))));

    if (getValidTestDatabase().isUseContentStore()) {
      return options(nexusBase, editConfigurationFileExtend(NEXUS_PROPERTIES_FILE, "nexus.datastore.enabled", "true"));
    }
    return nexusBase;
  }

  /**
   * This initialize method will add the PyPi resources test data directory and create the {@link #pyPiCli}.
   */
  @Before
  public void onInitializeClientIT() throws Exception {
    addTestDataDirectory("target/it-resources/pypi");

    pyPiCli = new PyPiCommandLineITSupport(createTestConfig());
    pyPiCli.exec(format("echo \"%s %s\" >> /etc/hosts", findLocalHostAddress(), DOCKER_HOST_NAME));
  }

  /**
   * This tear down method will assure that the {@link #pyPiCli} gets exited.
   */
  @After
  public void onTearDownClientIT() {
    pyPiCli.exit();
  }

  protected PyPiClient createPyPiClient(final String repositoryName) {
    return pyPiClientFactory
        .createClient(resolveUrl(nexusUrl, "/repository/" + repositoryName + "/"), "admin", "admin123");
  }

  protected File writeTmpPipConf(final String repoNamePath, final String repoFileName) throws Exception {
    File file = new File(rootTemporaryFolder, repoFileName);
    write(readTestDataFile(repoFileName)
        .replaceAll("\\$\\{baseUrl}", repoNamePath)
        .replaceAll("\\$\\{trustedHostIp}", findLocalHostAddress())
        .replaceAll("\\$\\{trustedHostName}", DOCKER_HOST_NAME)
        .getBytes(UTF_8), file);
    return file;
  }

  protected File writeTmpPyPiRc(final String repoNamePath, final String repoFileName) throws Exception {
    File file = new File(rootTemporaryFolder, repoFileName);
    write(readTestDataFile(repoFileName)
        .replaceAll("\\$\\{baseUrl}", repoNamePath)
        .getBytes(UTF_8), file);
    return file;
  }

  protected File downloadFromPyPiRepo(final PyPiClient client, final String clientPath, final String fileName)
      throws IOException
  {
    HttpResponse response = client.get(clientPath);
    assertThat(status(response), is(OK));

    File local = downloadFromHttpResponse(response, fileName);
    assertTrue(local.exists());
    return local;
  }

  protected void assertPackageNotInstalled(final String packageName) {
    Map<String, String> showValues = pyPiCli.pipShow(packageName);
    assertThat(showValues.get(PYTHON_PACKAGE_VERSION), nullValue());
    assertThat(showValues.get(PYTHON_PACKAGE_NAME), nullValue());
  }

  protected void assertPackageInstalledCorrectly(final String packageName) throws Exception {
    Map<String, String> showValues = pyPiCli.pipShow(packageName);
    assertPackageInstalledCorrectly(showValues.get(PYTHON_PACKAGE_NAME), showValues.get(PYTHON_PACKAGE_VERSION));
  }

  protected void assertPackageInstalledCorrectly(final String name, final String version) throws Exception {
    waitFor(() -> searchTesthelper.queryService().
        browse(unrestricted(boolQuery()
            .filter(termQuery(PYTHON_PACKAGE_NAME + ".raw", name))
            .filter(termQuery(PYTHON_PACKAGE_VERSION, version))))
        .iterator()
        .hasNext(), 2000);
  }

  protected void assertPackageUploaded(final PyPiClient pyPiClient, final String repoPath, final String localFile) throws IOException {
    assertTrue(contentEquals(downloadFromPyPiRepo(pyPiClient, repoPath, localFile), testData.resolveFile(localFile)));
  }
}
