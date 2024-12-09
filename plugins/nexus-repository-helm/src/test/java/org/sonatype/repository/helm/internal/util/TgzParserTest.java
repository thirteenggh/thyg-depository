package org.sonatype.repository.helm.internal.util;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TgzParserTest
    extends TestSupport
{
  private TgzParser underTest;

  @Before
  public void setUp() {
    this.underTest = new TgzParser();
  }

  @Test
  public void getYamlFromTgzTestCustomArchive() throws Exception {
    InputStream is = getClass().getResourceAsStream("mongodb-4.0.4.tgz");
    InputStream chartFromInputStream = underTest.getChartFromInputStream(is);
    String fileContent = IOUtils.toString(new InputStreamReader(chartFromInputStream));

    String expected = "appVersion: 3.6.6\n"
        + "description: NoSQL document-oriented database that stores JSON-like documents with\n"
        + "  dynamic schemas, simplifying the integration of data in content-driven applications.\n"
        + "engine: gotpl\n"
        + "home: https://mongodb.org\n"
        + "icon: https://bitnami.com/assets/stacks/mongodb/img/mongodb-stack-220x234.png\n"
        + "keywords:\n"
        + "- mongodb\n"
        + "- database\n"
        + "- nosql\n"
        + "- cluster\n"
        + "- replicaset\n"
        + "- replication\n"
        + "maintainers:\n"
        + "- email: containers@bitnami.com\n"
        + "  name: Bitnami\n"
        + "name: mongodb\n"
        + "sources:\n"
        + "- https://github.com/bitnami/bitnami-docker-mongodb\n"
        + "version: 4.0.4\n";
    assertThat(fileContent, is(expected));
  }

  @Test(expected = IllegalArgumentException.class)
  public void chartNotFound() throws Exception {
    InputStream is = getClass().getResourceAsStream("mysql_negative-1.4.0.tgz");
    underTest.getChartFromInputStream(is);
  }
}
