package org.apache.shiro.nexus;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom {@link SessionDAO}.
 *
 * @since 3.0
 */
public class NexusSessionDAO
  extends EnterpriseCacheSessionDAO
{
  private static final Logger log = LoggerFactory.getLogger(NexusSessionDAO.class);

  @Override
  protected Serializable doCreate(final Session session) {
    Serializable id = super.doCreate(session);
    log.trace("Created session-id: {} for session: {}", id, session);
    return id;
  }
}
