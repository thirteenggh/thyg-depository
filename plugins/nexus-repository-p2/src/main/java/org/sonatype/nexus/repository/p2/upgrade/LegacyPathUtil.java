package org.sonatype.nexus.repository.p2.upgrade;

/**
 * @since 3.28
 */
public class LegacyPathUtil
{
  private LegacyPathUtil() {
    // instance not allowed
  }

  private static final String HTTP_NXRM_PREFIX = "http/";

  private static final String HTTPS_NXRM_PREFIX = "https/";

  private static final String HTTP_URL_PREFIX = "http://";

  private static final String HTTPS_URL_PREFIX = "https://";

  /**
   * Convert a legacy URI encoded path back to a URI
   */
  public static String unescapePathToUri(final String path) {
    String resultPath = path;
    if (path.startsWith(HTTP_NXRM_PREFIX)) {
      resultPath = path.replaceFirst(HTTP_NXRM_PREFIX, HTTP_URL_PREFIX);
    }
    else if (path.startsWith(HTTPS_NXRM_PREFIX)) {
      resultPath = path.replaceFirst(HTTPS_NXRM_PREFIX, HTTPS_URL_PREFIX);
    }
    return resultPath;
  }
}
