package org.sonatype.repository.helm.internal.util;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.repository.helm.HelmAttributes;
import org.sonatype.repository.helm.internal.AssetKind;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HelmAttributeParserTest
    extends TestSupport
{
  private YamlParser yamlParser;

  private TgzParser tgzParser;

  private ProvenanceParser provenanceParser;

  private HelmAttributeParser underTest;

  @Before
  public void setUp() {
    yamlParser = new YamlParser();
    tgzParser = new TgzParser();
    provenanceParser = new ProvenanceParser();
    underTest = new HelmAttributeParser(tgzParser, yamlParser, provenanceParser);
  }

  @Test
  public void testGetAttributesFromChart() throws Exception {
    String name = "mongodb-0.4.9.tgz";
    InputStream chart = getClass().getResourceAsStream(name);
    AssetKind assetKind = AssetKind.getAssetKindByFileName(name);
    HelmAttributes result = underTest.getAttributes(assetKind, chart);

    assertThat(result.getName(), is("mongodb"));
    assertThat(result.getVersion(), is("0.4.9"));
    assertThat(result.getDescription(),
        is("NoSQL document-oriented database that stores JSON-like documents with dynamic schemas, simplifying the integration of data in content-driven applications."));
    assertThat(result.getIcon(), is("https://bitnami.com/assets/stacks/mongodb/img/mongodb-stack-220x234.png"));

    Map<String, String> maintainers = new LinkedHashMap<String, String>()
    {{
      put("email", "containers@bitnami.com");
      put("name", "Bitnami");
    }};
    assertThat(result.getMaintainers(), is(Collections.singletonList(maintainers)));
    assertThat(result.getSources(), is(Collections.singletonList("https://github.com/bitnami/bitnami-docker-mongodb")));
  }
}
