package org.sonatype.nexus.testsuite.raw;

import java.net.URL;

import org.sonatype.nexus.content.testsuite.groups.OrientAndSQLTestGroup;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.http.HttpStatus;
import org.sonatype.nexus.testsuite.testsupport.maven.MavenDeployment;
import org.sonatype.nexus.testsuite.testsupport.maven.MavenRunner;
import org.sonatype.nexus.testsuite.testsupport.raw.RawClient;
import org.sonatype.nexus.testsuite.testsupport.raw.RawITSupport;

import org.apache.http.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.sonatype.nexus.testsuite.testsupport.FormatClientSupport.asString;
import static org.sonatype.nexus.testsuite.testsupport.FormatClientSupport.status;

/**
 * Tests deployment of a maven site to a raw hosted repository.
 */
@Category(OrientAndSQLTestGroup.class)
public class RawMavenSiteIT
    extends RawITSupport
{
  private static String INDEX_HTML = "index.html";
  private Repository repository;

  private RawClient client;

  public RawMavenSiteIT() {
    testData.addDirectory(resolveBaseFile("target/it-resources/maven"));
  }

  @Before
  public void createHostedRepository() throws Exception {
    repository = repos.createRawHosted("test-raw-repo");
    client = rawClient(repository);
  }

  @Test
  public void deploySimpleSite() throws Exception {
    runMavenSite();

    final HttpResponse index = client.get(INDEX_HTML);

    assertThat(status(index), is(HttpStatus.OK));
    assertThat(asString(index), containsString("About testproject"));
  }

  @Test
  public void mkcolMethodNotAllowed() throws Exception {
    runMavenSite();

    final HttpResponse index = client.mkcol(INDEX_HTML);

    assertThat(status(index), is(HttpStatus.METHOD_NOT_ALLOWED));
  }

  @Test
  public void setLastDownloadOnGetNotPut() throws Exception {
    runMavenSite();

    assertThat(getLastDownloadedTime(repository, INDEX_HTML), is(equalTo(null)));
    final HttpResponse index = client.get(INDEX_HTML);

    assertThat(status(index), is(HttpStatus.OK));
    assertThat(asString(index), containsString("About testproject"));
    assertThat(getLastDownloadedTime(repository, INDEX_HTML).isBeforeNow(), is(equalTo(true)));
  }

  @Test
  public void setLastDownloadOnGet_IndexHtmlForwardHandler() throws Exception {
    runMavenSite();

    assertThat(getLastDownloadedTime(repository, INDEX_HTML), is(equalTo(null)));

    //This forces a path through the IndexHtmlForwardHandler
    final HttpResponse index = client.get("");

    assertThat(status(index), is(HttpStatus.OK));
    assertThat(asString(index), containsString("About testproject"));
    assertThat(getLastDownloadedTime(repository, INDEX_HTML).isBeforeNow(), is(equalTo(true)));
  }

  private void runMavenSite() throws Exception {
    String project = "testproject";
    URL siteUrl = new URL(nexusUrl, "/repository/" + repository.getName());

    MavenDeployment mavenDeployment = new MavenDeployment();
    mavenDeployment.setEnsureCleanOnInit(false);
    mavenDeployment.setVersion("version");
    mavenDeployment.setSettingsTemplate(resolveTestFile("settings.xml"));
    mavenDeployment.setProjectTemplateDir(resolveTestFile(project));
    mavenDeployment.setProjectDir(resolveBaseFile("target/raw-mvn-site/" + project).getAbsoluteFile());
    mavenDeployment.setProxyUrl(new URL(nexusUrl, "/repository/maven-public"));
    mavenDeployment.setDeployUrl(siteUrl);  // dummy deployUrl since we only run the site
    mavenDeployment.setSiteUrl(siteUrl);
    mavenDeployment.init();

    new MavenRunner().run(mavenDeployment,  "clean", "site:site", "site:deploy");
  }
}
