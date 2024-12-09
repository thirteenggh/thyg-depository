package org.sonatype.nexus.rapture.internal.security;

import java.io.IOException;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.net.HttpHeaders.X_FRAME_OPTIONS;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static org.sonatype.nexus.servlet.XFrameOptions.DENY;

/**
 * Session servlet, to expose end-point for configuration of Shiro authentication filter to
 * establish a user session.
 *
 * @since 3.0
 *
 * @see SessionAuthenticationFilter
 */
@Named
@Singleton
public class SessionServlet
  extends HttpServlet
{
  private static final Logger log = LoggerFactory.getLogger(SessionServlet.class);

  /**
   * Create session.
   */
  @Override
  protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException
  {
    Subject subject = SecurityUtils.getSubject();
    log.info("Created session for user: {}", subject.getPrincipal());

    // sanity check
    checkState(subject.isAuthenticated());
    checkState(subject.getSession(false) != null);

    response.setStatus(SC_NO_CONTENT);

    // Silence warnings about "clickjacking" (even though it doesn't actually apply to API calls)
    response.setHeader(X_FRAME_OPTIONS, DENY);
  }

  /**
   * Delete session.
   */
  @Override
  protected void doDelete(final HttpServletRequest request, final HttpServletResponse response)
      throws ServletException, IOException
  {
    Subject subject = SecurityUtils.getSubject();
    log.info("Deleting session for user: {}", subject.getPrincipal());
    subject.logout();

    // sanity check
    checkState(!subject.isAuthenticated());
    checkState(!subject.isRemembered());
    checkState(subject.getSession(false) == null);

    response.setStatus(SC_NO_CONTENT);

    // Silence warnings about "clickjacking" (even though it doesn't actually apply to API calls)
    response.setHeader(X_FRAME_OPTIONS, DENY);
  }
}
