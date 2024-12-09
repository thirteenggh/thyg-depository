package org.sonatype.nexus.repository.r.internal.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;

import org.sonatype.nexus.repository.r.internal.RException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Utility methods for working with R metadata in general.
 *
 * @since 3.28
 */
public final class RMetadataUtils
{
  /**
   * Parses metadata stored in a Debian Control File-like format.
   *
   * @see <a href="https://cran.r-project.org/doc/manuals/r-release/R-exts.html#The-DESCRIPTION-file">Description File</a>
   */
  public static Map<String, String> parseDescriptionFile(final InputStream in) {
    checkNotNull(in);
    try {
      LinkedHashMap<String, String> results = new LinkedHashMap<>();
      InternetHeaders headers = new InternetHeaders(in);
      Enumeration headerEnumeration = headers.getAllHeaders();
      while (headerEnumeration.hasMoreElements()) {
        Header header = (Header) headerEnumeration.nextElement();
        String name = header.getName();
        String value = header.getValue()
            .replace("\r\n", "\n")
            .replace("\r", "\n"); // TODO: "should" be ASCII only, otherwise need to know encoding?
        results.put(name, value); // TODO: Supposedly no duplicates, is this true?
      }
      return results;
    } catch (MessagingException e) {
      throw new RException(null, e);
    }
  }

  private RMetadataUtils() {
    // empty
  }
}
