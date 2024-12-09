
package org.sonatype.nexus.security.config;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static org.sonatype.nexus.security.user.UserManager.DEFAULT_SOURCE;

/**
 * @since 3.20
 */
public class SecuritySourceUtil
{
  private static final Set<String> CASE_INSENSITIVE_SOURCES = newHashSet(DEFAULT_SOURCE.toLowerCase(), "crowd", "ldap");

  public static boolean isCaseInsensitiveSource(final String source) {
    return CASE_INSENSITIVE_SOURCES.contains(source.toLowerCase());
  }
}
