package org.sonatype.repository.helm.internal.util;

import java.io.InputStream;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.repository.helm.HelmAttributes;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ProvenanceParserTest
    extends TestSupport
{
  private ProvenanceParser provenanceParser;

  @Before
  public void setUp() {
    this.provenanceParser = new ProvenanceParser();
  }

  @Test
  public void parseProv() throws Exception {
    InputStream is = getClass().getResourceAsStream("mysql-1.4.0.tgz.prov");
    HelmAttributes attributes = provenanceParser.parse(is);

    assertThat(attributes.getName(), is(equalTo("mysql")));
    assertThat(attributes.getDescription(), is(equalTo("Fast, reliable, scalable, and easy to use open-source relational database system.")));
    assertThat(attributes.getVersion(), is(equalTo("1.4.0")));
    assertThat(attributes.getIcon(), is(equalTo("https://www.mysql.com/common/logos/logo-mysql-170x115.png")));
    assertThat(attributes.getAppVersion(), is(equalTo("5.7.27")));
  }
}
