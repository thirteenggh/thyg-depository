package org.sonatype.nexus.testsuite.testsupport.pypi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.sonatype.goodies.common.ComponentSupport;

import com.google.common.annotations.VisibleForTesting;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Test utility class for building PyPi packages.
 *
 * @since 3.15
 */
public class PyPiPackageGenerator
    extends ComponentSupport
{
  private static final String TEMPLATE = "Metadata-Version: 2.0%n" +
      "Name: %s%n" +
      "Version: %s%n" +
      "Summary: %s%n" +
      "License: MIT%n" +
      "A test Python project";

  @VisibleForTesting
  static final String METADATA_FILE_NAME = "METADATA";

  public InputStream buildWheel(final String name, final String version) throws IOException {
    String metadataFile = String.format(TEMPLATE, name, version, name);
    
    return buildZipWithFileAndContents(METADATA_FILE_NAME, metadataFile);
  }

  private InputStream buildZipWithFileAndContents(final String fileName, final String contents) throws IOException
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (ZipOutputStream zipOut = new ZipOutputStream(out)) {
      zipOut.putNextEntry(new ZipEntry(fileName));
      zipOut.write(contents.getBytes(UTF_8));
      zipOut.closeEntry();
    }

    return new ByteArrayInputStream(out.toByteArray());
  }
}
