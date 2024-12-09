package org.sonatype.nexus.testsuite.testsupport.maven;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.testsupport.TestData;
import org.sonatype.nexus.common.app.BaseUrlHolder;
import org.sonatype.nexus.content.maven.MavenContentFacet;
import org.sonatype.nexus.content.maven.MavenMetadataRebuildContentFacet;
import org.sonatype.nexus.pax.exam.NexusPaxExamSupport;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.maven.MavenPath;
import org.sonatype.nexus.repository.view.Payload;

import com.google.common.base.Strings;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.maven.artifact.repository.metadata.Metadata;
import org.apache.maven.artifact.repository.metadata.io.xpp3.MetadataXpp3Reader;
import org.joda.time.DateTime;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public abstract class MavenTestHelper
{
  private final MetadataXpp3Reader reader = new MetadataXpp3Reader();

  @Inject
  @Named("http://localhost:${application-port}${nexus-context-path}")
  private URL nexusUrl;

  private CloseableHttpClient client = HttpClientBuilder.create().build();

  public Payload read(final Repository repository, final String path) throws IOException {
    MavenContentFacet mavenFacet = repository.facet(MavenContentFacet.class);
    Optional<MavenMetadataRebuildContentFacet> metadataRebuildFacet =
        repository.optionalFacet(MavenMetadataRebuildContentFacet.class);
    if (metadataRebuildFacet.isPresent()) {
      metadataRebuildFacet.get().maybeRebuildMavenMetadata(path, false, true);
    }
    MavenPath mavenPath = mavenFacet.getMavenPathParser().parsePath(path);
    return mavenFacet.get(mavenPath).orElse(null);
  }

  public abstract void write(final Repository repository, final String path, final Payload payload) throws IOException;

  public abstract void writeWithoutValidation(Repository repository, String path, Payload payload) throws IOException;

  public abstract void verifyHashesExistAndCorrect(Repository repository, String path) throws Exception;

  public abstract DateTime getLastDownloadedTime(final Repository repository, final String assetPath)
      throws IOException;

  public Metadata parseMetadata(final Repository repository, final String path) throws Exception {
    try (Payload payload = read(repository, path)) {
      return parseMetadata(payload.openInputStream());
    }
  }

  public void mvnDeploy(
      final TestData testData,
      final String project,
      final String version,
      final String deployRepositoryName) throws Exception
  {
    mvnDeploy(testData, project, version, new URL(nexusUrl,
        "/repository/maven-public"),
        new URL(nexusUrl, "/repository/" + deployRepositoryName));
  }

  public void mvnDeploy(
      final TestData testData,
      final String project,
      final String group,
      final String artifactId,
      final String version,
      final String deployRepositoryName) throws Exception
  {
    mvnDeploy(testData, project, group, artifactId, version, new URL(nexusUrl,
        "/repository/maven-public"),
        new URL(nexusUrl, "/repository/" + deployRepositoryName));
  }

  protected void mvnLegacyDeploy(
      final TestData testData,
      final String project,
      final String version,
      final String deployRepositoryName) throws Exception
  {
    mvnDeploy(testData, project, version, new URL(nexusUrl,
        "/repository/maven-public"),
        new URL(nexusUrl, "/content/repositories/" + deployRepositoryName));
  }

  protected void mvnDeploy(
      final TestData testData,
      final String project,
      final String version,
      final URL proxyUrl,
      final URL deployUrl) throws Exception
  {
    final File mavenBaseDir = mvnBaseDir(project).getAbsoluteFile();
    final File projectDir = testData.resolveFile(project);

    MavenDeployment mavenDeployment = new MavenDeployment();
    mavenDeployment.setSettingsTemplate(testData.resolveFile("settings.xml"));
    mavenDeployment.setProjectDir(mavenBaseDir);
    mavenDeployment.setProjectTemplateDir(projectDir);
    mavenDeployment.setVersion(version);
    mavenDeployment.setProxyUrl(proxyUrl);
    mavenDeployment.setDeployUrl(deployUrl);
    mavenDeployment.setEnsureCleanOnInit(false);
    mavenDeployment.init();

    new MavenRunner().run(mavenDeployment, "clean", "deploy");
  }

  protected void mvnDeploy(
      final TestData testData,
      final String project,
      final String group,
      final String artifactId,
      final String version,
      final URL proxyUrl,
      final URL deployUrl)
  {
    final File mavenBaseDir = mvnBaseDir(project).getAbsoluteFile();
    final File projectDir = testData.resolveFile(project);

    MavenDeployment mavenDeployment = new MavenDeployment();
    mavenDeployment.setSettingsTemplate(testData.resolveFile("settings.xml"));
    mavenDeployment.setProjectDir(mavenBaseDir);
    mavenDeployment.setProjectTemplateDir(projectDir);
    mavenDeployment.setGroupId(group);
    mavenDeployment.setArtifactId(artifactId);
    mavenDeployment.setVersion(version);
    mavenDeployment.setProxyUrl(proxyUrl);
    mavenDeployment.setDeployUrl(deployUrl);
    mavenDeployment.setEnsureCleanOnInit(false);
    mavenDeployment.init();

    new MavenRunner().run(mavenDeployment, "clean", "deploy");
  }

  private File mvnBaseDir(final String project) {
    return NexusPaxExamSupport
        .resolveBaseFile("target/" + getClass().getSimpleName() + "-" + Math.random() + "/" + project);
  }

  public Metadata parseMetadata(final InputStream is) throws Exception {
    try {
      assertThat(is, notNullValue());
      return reader.read(is);
    }
    finally {
      IOUtils.closeQuietly(is);
    }
  }

  private String url(final Repository repo, final String path) {
    boolean reset = false;
    if (!BaseUrlHolder.isSet()) {
      reset = true;
      BaseUrlHolder.set(nexusUrl.toString());
    }
    try {
      String repoPath = encode(path.startsWith("/") ? path : '/' + path, UTF_8.toString());
      return String.format("%s%s", repo.getUrl(), repoPath);
    }
    catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    finally {
      if (reset) {
        BaseUrlHolder.unset();
      }
    }
  }

  public void rebuildMetadata(
      final Repository repository,
      final String groupId,
      final String artifactId,
      final String baseVersion,
      final boolean rebuildChecksums) {
    final boolean update = !Strings.isNullOrEmpty(groupId)
        || !Strings.isNullOrEmpty(artifactId)
        || !Strings.isNullOrEmpty(baseVersion);
    rebuildMetadata(repository, groupId, artifactId, baseVersion, rebuildChecksums, update);
  }

  public abstract void rebuildMetadata(
      final Repository repository,
      final String groupId,
      final String artifactId,
      final String baseVersion,
      final boolean rebuildChecksums,
      final boolean update);

  /**
   * Delete test Components with the given version, confirming that a certain number exist first.
   */
  public abstract void deleteComponents(final Repository repository, final String version, final int expectedNumber);

  public abstract void deleteAssets(final Repository repository, final String version, final int expectedNumber);


  /**
   * Create component with given GAV and attached JAR asset
   *
   * @return componentId
   */
  public abstract String createComponent(
      final Repository repository,
      final String groupId,
      final String artifactId,
      final String version);
}
