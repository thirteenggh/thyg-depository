package org.sonatype.nexus.repository.maven.internal;

import java.io.ByteArrayInputStream;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.maven.model.Model;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;

public class MavenModelsTest
    extends TestSupport
{
  String notXml = "not xml";

  @Test
  public void testReadModel_emptyInputStreamIsNull() throws Exception {
    Model model = MavenModels.readModel(new ByteArrayInputStream(new byte[0]));
    assertThat(model, nullValue());
  }

  @Test
  public void testReadModel_NotXmlIsNull() throws Exception {
    Model model = MavenModels.readModel(new ByteArrayInputStream(notXml.getBytes()));
    assertThat(model, nullValue());
  }
}
