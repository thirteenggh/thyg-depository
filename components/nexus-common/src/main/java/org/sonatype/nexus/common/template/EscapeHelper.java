package org.sonatype.nexus.common.template;

import org.sonatype.nexus.common.encoding.EncodingUtil;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Helper to escape values.
 *
 * @since 3.0
 */
@TemplateAccessible
public class EscapeHelper
{
  public String html(final String value) {
    return StringEscapeUtils.escapeHtml(value);
  }

  public String html(final Object value) {
    return html(String.valueOf(value));
  }

  public String url(final String value) {
    if (value == null || value.isEmpty()) {
      return value;
    }
    else {
      return EncodingUtil.urlEncode(value);
    }
  }

  public String url(final Object value) {
    return url(String.valueOf(value));
  }

  public String xml(final String value) {
    return StringEscapeUtils.escapeXml(value);
  }

  public String xml(final Object value) {
    return xml(String.valueOf(value));
  }

  public String uri(final String value) {
    if (value == null || value.isEmpty()) {
      return value;
    }
    else {
      return url(value)
          .replaceAll("\\+", "%20")
          .replaceAll("\\%21", "!")
          .replaceAll("\\%27", "'")
          .replaceAll("\\%28", "(")
          .replaceAll("\\%29", ")")
          .replaceAll("\\%7E", "~");
    }
  }

  public String uri(final Object value) {
    return uri(String.valueOf(value));
  }

  /**
   * Strip java el start token from a string
   * @since 3.14
   */
  public String stripJavaEl(final String value) {
    if (value != null) {
      return value.replaceAll("\\$+\\{", "{").replaceAll("\\$+\\\\A\\{", "{");
    }
    return null;
  }
}
