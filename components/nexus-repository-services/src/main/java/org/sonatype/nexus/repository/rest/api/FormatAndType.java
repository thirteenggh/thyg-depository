package org.sonatype.nexus.repository.rest.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.sonatype.nexus.rest.WebApplicationMessageException;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * Extracts the format and type from the {@link UriInfo} path for use in resource handlers
 *
 * @since 3.28
 */
public class FormatAndType
{
  private static final Pattern GET_REPO_PATH = Pattern.compile(".+/(.+)/(hosted|group|proxy)/(?!/).+");

  private String format;

  private String type;

  @Context
  public void setUriInfo(final UriInfo uriInfo) {
    Matcher matcher = GET_REPO_PATH.matcher(uriInfo.getPath());
    if (matcher.matches()) {
      this.format = "maven".equals(matcher.group(1)) ? "maven2" : matcher.group(1);
      this.type = matcher.group(2);
    } else {
      throw new WebApplicationMessageException(NOT_FOUND, "\"Repository not found\"", APPLICATION_JSON);
    }
  }

  public String type() {
    return type;
  }

  public String format() {
    return format;
  }
}
