package org.apache.shiro.nexus;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom {@link SessionManager} for tests.
 */
public class TestSessionManager
    extends DefaultSessionManager
{
  private static final Logger log = LoggerFactory.getLogger(TestSessionManager.class);

  @Inject
  public void configureProperties(
      @Named("${shiro.globalSessionTimeout:-" + DEFAULT_GLOBAL_SESSION_TIMEOUT + "}") final long globalSessionTimeout)
  {
    setGlobalSessionTimeout(globalSessionTimeout);
  }

  /**
   * See https://issues.sonatype.org/browse/NEXUS-5727, https://issues.apache.org/jira/browse/SHIRO-443
   */
  @Override
  protected synchronized void enableSessionValidation() {
    final SessionValidationScheduler scheduler = getSessionValidationScheduler();
    if (scheduler == null) {
      log.info("Global session timeout: {} ms", getGlobalSessionTimeout());
      super.enableSessionValidation();
    }
  }
}
