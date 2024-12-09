package org.sonatype.nexus.security.anonymous;

import javax.annotation.Nullable;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * Anonymous helpers.
 *
 * @since 3.0
 */
public class AnonymousHelper
{
  private AnonymousHelper() {
    // empty
  }

  /**
   * Check given given subject is anonymous.
   */
  public static boolean isAnonymous(@Nullable final Subject subject) {
    return subject != null && subject.getPrincipals() instanceof AnonymousPrincipalCollection;
  }

  /**
   * Check given principals represent anonymous.
   *
   * @since 3.22
   */
  public static boolean isAnonymous(@Nullable final PrincipalCollection principals) {
    return principals instanceof AnonymousPrincipalCollection;
  }
}
