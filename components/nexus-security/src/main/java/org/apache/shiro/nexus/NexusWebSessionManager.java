package org.apache.shiro.nexus;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom {@link WebSessionManager}.
 */
public class NexusWebSessionManager
    extends DefaultWebSessionManager
{
  private static final Logger log = LoggerFactory.getLogger(NexusWebSessionManager.class);

  private static final String DEFAULT_NEXUS_SESSION_COOKIE_NAME = "NXSESSIONID";

  @Inject
  public void configureProperties(
      @Named("${shiro.globalSessionTimeout:-" + DEFAULT_GLOBAL_SESSION_TIMEOUT + "}") final long globalSessionTimeout,
      @Named("${nexus.sessionCookieName:-" + DEFAULT_NEXUS_SESSION_COOKIE_NAME + "}") final String sessionCookieName)
  {
    setGlobalSessionTimeout(globalSessionTimeout);
    log.info("Global session timeout: {} ms", getGlobalSessionTimeout());

    Cookie cookie = getSessionIdCookie();
    cookie.setName(sessionCookieName);
    log.info("Session-cookie prototype: name={}", cookie.getName());
  }

  /**
   * See https://issues.sonatype.org/browse/NEXUS-5727, https://issues.apache.org/jira/browse/SHIRO-443
   */
  @Override
  protected synchronized void enableSessionValidation() {
    final SessionValidationScheduler scheduler = getSessionValidationScheduler();
    if (scheduler == null) {
      super.enableSessionValidation();
    }
  }
}
