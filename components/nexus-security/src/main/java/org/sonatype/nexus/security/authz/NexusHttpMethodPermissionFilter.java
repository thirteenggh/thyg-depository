package org.sonatype.nexus.security.authz;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nexus {@link HttpMethodPermissionFilter}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class NexusHttpMethodPermissionFilter
  extends HttpMethodPermissionFilter
{
  public static final String NAME = "nx-http-permissions";

  protected final Logger log = LoggerFactory.getLogger(getClass());
}
