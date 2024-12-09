package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;
import java.util.Optional;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.p2.internal.exception.AttributeParsingException;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * Utility methods for working with Jar (Jar Binks, worst character) files
 *
 * @since 3.28
 */
public abstract class JarExtractor<T>
{
  private TempBlobConverter tempBlobConverter;
  public JarExtractor(final TempBlobConverter tempBlobConverter){
    this.tempBlobConverter = tempBlobConverter;
  }

  protected Optional<T> getSpecificEntity(
      final TempBlob tempBlob,
      final String extension,
      @Nullable final String startNameForSearch) throws IOException, AttributeParsingException
  {
    try (JarInputStream jis = getJarStreamFromBlob(tempBlob, extension)) {
      JarEntry jarEntry;
      while ((jarEntry = jis.getNextJarEntry()) != null) {
        if (startNameForSearch != null && jarEntry.getName().startsWith(startNameForSearch)) {
          return Optional.ofNullable(createSpecificEntity(jis, jarEntry));
        }
      }
    }

    return Optional.empty();
  }

  protected abstract T createSpecificEntity(final JarInputStream jis, final JarEntry jarEntry) throws IOException, AttributeParsingException;

  private JarInputStream getJarStreamFromBlob(final TempBlob tempBlob, final String extension) throws IOException {
    if (extension.equals("jar")) {
      return new JarInputStream(tempBlob.get());
    }
    else {
      return new JarInputStream(tempBlobConverter.getJarFromPackGz(tempBlob));
    }
  }
}
