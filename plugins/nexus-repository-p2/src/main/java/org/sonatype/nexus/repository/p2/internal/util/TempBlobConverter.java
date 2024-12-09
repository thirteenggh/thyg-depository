package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.Unpacker;
import java.util.zip.GZIPInputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

import static java.nio.file.Files.createTempFile;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

/**
 * Utility methods for turning a TempBlob into thing(s)
 */
@Named
@Singleton
public class TempBlobConverter
    extends ComponentSupport
{
  public TempBlobConverter() {
    // no-op
  }

  public InputStream getJarFromPackGz(final TempBlob tempBlob) throws IOException {
    Path tempFile = createTempFile("pack-file", "jar.pack");
    try (GZIPInputStream gzis = new GZIPInputStream(tempBlob.get()); OutputStream os = newOutputStream(tempFile)) {
      Unpacker unpacker = Pack200.newUnpacker();
      try (JarOutputStream jos = new JarOutputStream(os)) {
        unpacker.unpack(gzis, jos);
        return newInputStream(tempFile);
      }
    }
    finally {
      Files.delete(tempFile);
    }
  }
}
