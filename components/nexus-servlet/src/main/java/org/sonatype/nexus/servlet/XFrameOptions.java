
package org.sonatype.nexus.servlet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @since 3.11
 */
@Named
@Singleton
public class XFrameOptions
    implements Serializable
{
  private static final long serialVersionUID = 5092514762222572451L;

  private static final String COPYRIGHT_PATH = "/COPYRIGHT.html";

  private static final String HEALTHCHECK_PATH = "/static/healthcheck-tos.html";

  private static final String OSS_LICENSE_PATH = "/OSS-LICENSE.html";

  private static final String PRO_LICENSE_PATH = "/PRO-LICENSE.html";

  private static final String SWAGGER_UI = "/swagger-ui/";

  public static final String DENY = "DENY";

  public static final String SAME_ORIGIN = "SAMEORIGIN";

  private final boolean defaultDeny;

  private final Set<String> frameablePaths;

  @Inject
  public XFrameOptions(@Named("${nexus.http.denyframe.enabled:-true}") final boolean defaultDeny) {
    this.defaultDeny = defaultDeny;
    frameablePaths = new HashSet<>();
    frameablePaths.add(COPYRIGHT_PATH);
    frameablePaths.add(HEALTHCHECK_PATH);
    frameablePaths.add(OSS_LICENSE_PATH);
    frameablePaths.add(PRO_LICENSE_PATH);
    frameablePaths.add(SWAGGER_UI);
  }

  public String getValueForPath(final String path) {
    if (!defaultDeny || frameablePaths.contains(path)) {
      return SAME_ORIGIN;
    }
    return DENY;
  }
}
