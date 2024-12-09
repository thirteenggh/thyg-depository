package org.sonatype.nexus.repository.cocoapods.internal;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import static org.sonatype.nexus.repository.cocoapods.internal.git.GitConstants.GITHUB_POD_EXTENSION;

/**
 * @since 3.27
 */
public class PathUtils
{
  private static final String SPECS_PATH_FORMAT = "Specs/%s/%s/%s/%s.podspec.json";

  private static final String SPECS_SHARDS_PATH_FORMAT = "%s/%s/%s";

  private static final String API_GITHUB_HOST = "api.github.com";

  private PathUtils(){}

  public static String buildNxrmSpecFilePath(final String name, final String version)
  {
    return String.format(SPECS_PATH_FORMAT, getSpecShardPath(name), name, version, name);
  }

  public static String buildNxrmPodPath(
      final String name,
      final String version,
      final URI httpDownloadUri)
  {
    String fileName = FilenameUtils.getName(httpDownloadUri.getPath());

    if (httpDownloadUri.getHost().equals(API_GITHUB_HOST)) {
      if (StringUtils.isEmpty(fileName)) {
        fileName = version;
      }
      fileName += GITHUB_POD_EXTENSION;
    }

    return String.format("pods/%s/%s/%s", name, version, fileName);
  }

  /**
   * Cocoapods algorithm to put spec files into different folders (shards in therms of cocoapods)
   */
  private static String getSpecShardPath(final String name) {
    String nameHash = DigestUtils.md5Hex(name.getBytes(StandardCharsets.UTF_8));
    return String
        .format(SPECS_SHARDS_PATH_FORMAT, nameHash.substring(0, 1), nameHash.substring(1, 2), nameHash.substring(2, 3));
  }
}
