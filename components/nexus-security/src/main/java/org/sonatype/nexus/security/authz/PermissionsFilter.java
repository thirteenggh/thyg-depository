package org.sonatype.nexus.security.authz;

import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.base.Joiner;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Singleton
public class PermissionsFilter
  extends PermissionsAuthorizationFilter
{
  public static final String NAME = "nx-perms";

  /**
   * Helper to build filter configuration.
   */
  public static String config(final String... permissions) {
    checkNotNull(permissions);
    checkArgument(permissions.length != 0);
    return String.format("%s[%s]", NAME, Joiner.on(",").join(permissions));
  }
}
