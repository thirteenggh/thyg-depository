package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.util.List;

import org.sonatype.goodies.httpfixture.server.fluent.Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static org.sonatype.goodies.httpfixture.server.fluent.Behaviours.content;
import static org.sonatype.goodies.httpfixture.server.fluent.Behaviours.error;
import static org.sonatype.nexus.repository.view.ContentTypes.TEXT_HTML;

public class PyPiPackageListRetrieverTest
{
  private static final String REPOSITORY_NAME = "repo";

  private static final String INDEX =
      "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
          "     <title>Simple index</title>\n" +
          "   </head>\n" +
          "   <body>\n" +
          "     <a href=\"test1/\">test1</a>\n" +
          "     <a href=\"/test2/\">test2</a>\n" +
          " </body></html>";
  
  private static final String PACKAGE_INDEX_1 = "<html>\n" +
      "<head>\n" +
      "  <title>Links for sample</title>\n" +
      "  <meta name=\"api-version\" value=\"2\"/>\n" +
      "</head>\n" +
      "<body>\n" +
      "<h1>Links for sample</h1>\n" +
      "<a href=\"../../packages/00/01/02/test1.whl#md5=a55ba45d4acf405523c24d80214b7ac8\"\n" +
      "   rel=\"internal\">test1.whl</a><br/>\n" +
      "</body>\n" +
      "</html>";

  private static final String PACKAGE_INDEX_2 = "<html>\n" +
      "<head>\n" +
      "  <title>Links for sample</title>\n" +
      "  <meta name=\"api-version\" value=\"2\"/>\n" +
      "</head>\n" +
      "<body>\n" +
      "<h1>Links for sample</h1>\n" +
      "<a href=\"../../packages/00/01/02/test2.whl#md5=a55ba45d4acf405523c24d80214b7ac8\"\n" +
      "   rel=\"internal\">test2.whl</a><br/>\n" +
      "</body>\n" +
      "</html>";

  private Server server;

  @Before
  public void setUp() throws Exception {
    server = Server.withPort(0)
        .serve("/*").withBehaviours(error(200))
        .serve("/" + REPOSITORY_NAME + "/simple/").withBehaviours(content(INDEX, TEXT_HTML))
        .serve("/" + REPOSITORY_NAME + "/simple/test1/").withBehaviours(content(PACKAGE_INDEX_1, TEXT_HTML))
        .serve("/" + REPOSITORY_NAME + "/simple/test2/").withBehaviours(content(PACKAGE_INDEX_2, TEXT_HTML));
    
    server.start();
  }

  @After
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void getListOfIndexesViaRootIndex() throws Exception {
    String repoUrl = server.getUrl() + "/" + REPOSITORY_NAME;
    List<String> packages = new PyPiPackageListRetriever().getListOfIndexes(repoUrl);

    assertThat(packages, hasItem(indexUrlForPackage("test1")));
    assertThat(packages, hasItem(indexUrlForPackage("test2")));
  }

  @Test
  public void getListOfPackages() throws Exception {
    String repoUrl = server.getUrl() + "/" + REPOSITORY_NAME;
    String packageIndexUrl = server.getUrl() + "/" + REPOSITORY_NAME + "/simple/test1/";
    
    List<String> packages = new PyPiPackageListRetriever().getListOfPackages(repoUrl, packageIndexUrl);

    assertThat(packages, hasItem(packageUrlForPackage("test1")));
  }

  private String indexUrlForPackage(final String packageName) {
    return server.getUrl() + "/" + REPOSITORY_NAME + "/simple/" + packageName + "/";
  }

  private String packageUrlForPackage(final String packageName) {
    return server.getUrl() + "/" + REPOSITORY_NAME + "/packages/00/01/02/" + packageName + ".whl";
  }
}
