package org.sonatype.nexus.orient.testsupport.internal;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.LifecycleSupport;
import org.sonatype.nexus.orient.DatabaseServer;

import com.orientechnologies.orient.core.OConstants;
import com.orientechnologies.orient.core.Orient;

/**
 * Minimal {@link DatabaseServer}.
 *
 * @since 3.1
 */
@Named("minimal")
@Singleton
public class MinimalDatabaseServer
    extends LifecycleSupport
    implements DatabaseServer
{
  public MinimalDatabaseServer() {
    log.info("OrientDB version: {}", OConstants.getVersion());
  }

  @Override
  protected void doStart() throws Exception {
    Orient.instance().startup();
    Orient.instance().removeShutdownHook();
  }

  @Override
  protected void doStop() throws Exception {
    Orient.instance().shutdown();
  }

  @Override
  public List<String> databases() {
    return Collections.emptyList();
  }

  /**
   * Stop underlying server outside of normal lifecycle in order to test failure situations.
   */
  public void stopAbnormally() {
    Orient.instance().shutdown();
  }
}
