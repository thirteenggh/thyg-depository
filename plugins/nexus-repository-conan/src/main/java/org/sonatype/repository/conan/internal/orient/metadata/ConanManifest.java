package org.sonatype.repository.conan.internal.orient.metadata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.sonatype.goodies.common.Loggers;
import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.storage.TempBlob;

import org.slf4j.Logger;

/**
 * A conan manifest file contains md5 values for subsequent files
 *
 * @since 3.28
 */
public class ConanManifest
{
  private static final Logger LOGGER = Loggers.getLogger(ConanManifest.class);

  /**
   * Extract all the md5 for conan files
   * @param conanManifestContentStream
   * @return Returns {AttributesMap} of file path keys to file md5 hash values
   */
  public static AttributesMap parse(InputStream conanManifestContentStream) {
    AttributesMap attributesMap = new AttributesMap();
    try(BufferedReader reader = new BufferedReader(new InputStreamReader(conanManifestContentStream))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] split = line.split(":");
        if (split.length == 2) {
          attributesMap.set(split[0].trim(), split[1].trim());
        }
      }
    } catch (IOException e) {
      LOGGER.warn("Unable to convertKeys manifest file");
    }
    return attributesMap;
  }


  /**
   * Extract all the md5 for conan files
   * @param blob
   * @return Returns {AttributesMap} of file path keys to file md5 hash values
   */
  public static AttributesMap parse(TempBlob blob) {
    return parse(blob.get());
  }
}
