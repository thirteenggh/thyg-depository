package ch.qos.logback.access.jetty;

import org.eclipse.jetty.util.component.LifeCycle;

/**
 * Adapt Logback {@link RequestLogImpl} to Jetty {@link LifeCycle} for support of Jetty 9.3+.
 *
 * @since 3.0
 */
public class RequestLogImpl2
  extends RequestLogImpl
  implements LifeCycle
{
  // FIXME: remove when http://jira.qos.ch/browse/LOGBACK-1052 is fixed and new logback is released
}
