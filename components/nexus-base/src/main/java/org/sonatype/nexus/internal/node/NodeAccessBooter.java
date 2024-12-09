package org.sonatype.nexus.internal.node;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.lifecycle.Lifecycle;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.node.NodeAccess;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.STORAGE;

/**
 * {@link NodeAccess} booter.
 *
 * @since 3.24
 */
@Named
@ManagedLifecycle(phase = STORAGE)
@Priority(Integer.MAX_VALUE) // make sure this starts first
@Singleton
public class NodeAccessBooter
    implements Lifecycle
{
  private final NodeAccess nodeAccess;

  @Inject
  public NodeAccessBooter(final NodeAccess nodeAccess) {
    this.nodeAccess = checkNotNull(nodeAccess);
  }

  @Override
  public void start() throws Exception {
    nodeAccess.start();
  }

  @Override
  public void stop() throws Exception {
    nodeAccess.stop();
  }
}
