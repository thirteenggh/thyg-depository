package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.sonatype.goodies.testsupport.TestSupport;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.sonatype.nexus.testsuite.testsupport.pypi.PyPiPackageGenerator.METADATA_FILE_NAME;

public class PyPiPackageGeneratorTest
    extends TestSupport
{
  @Test
  public void shouldBuildWheelWithNameAndVersion() throws Exception {
    String name = "test";
    String version = "1.1.0";
    InputStream wheel = new PyPiPackageGenerator().buildWheel(name, version);

    String metadata = extractMetadataFile(wheel);

    assertThat(metadata, containsString("Metadata-Version: 2.0" + System.lineSeparator() +
        "Name: test" + System.lineSeparator() +
        "Version: 1.1.0" + System.lineSeparator() +
        "Summary: test" + System.lineSeparator() +
        "License: MIT" + System.lineSeparator() +
        "A test Python project"));
  }

  private String extractMetadataFile(final InputStream wheel) throws IOException {
    try (ZipInputStream zip = new ZipInputStream(wheel)) {
      ZipEntry entry;
      while ((entry = zip.getNextEntry()) != null) {
        if (entry.getName().equals(METADATA_FILE_NAME)) {
          return IOUtils.toString(zip);
        }
      }
    }
    return null;
  }
}
