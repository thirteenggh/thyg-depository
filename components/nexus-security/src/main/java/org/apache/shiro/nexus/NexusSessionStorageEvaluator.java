package org.apache.shiro.nexus;

import org.sonatype.nexus.security.anonymous.AnonymousHelper;

import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;

/**
 * Custom {@link SessionStorageEvaluator}.
 *
 * @since 3.0
 */
public class NexusSessionStorageEvaluator
  extends DefaultWebSessionStorageEvaluator
{
  /**
   * Disable storage for anonymous subject.
   */
  @Override
  public boolean isSessionStorageEnabled(final Subject subject) {
    return !AnonymousHelper.isAnonymous(subject) && super.isSessionStorageEnabled(subject);
  }
}
