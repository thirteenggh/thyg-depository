package org.sonatype.nexus.internal.node;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.DeploymentAccess;
import org.sonatype.nexus.common.node.NodeAccess;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple {@link DeploymentAccess} that just delegates to {@link NodeAccess} and doesn't allow aliases.
 *
 * @since 3.21
 */
@Named
@Singleton
public class SimpleDeploymentAccess
    implements DeploymentAccess
{
  private final NodeAccess nodeAccess;

  @Inject
  public SimpleDeploymentAccess(final NodeAccess nodeAccess) {
    this.nodeAccess = checkNotNull(nodeAccess);
  }

  @Override
  public String getId() {
    return nodeAccess.getId();
  }

  @Override
  public String getAlias() {
    return null; // alias is never set
  }

  @Override
  public void setAlias(final String newAlias) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void start() {
    // no-op
  }

  @Override
  public void stop() {
    // no-op
  }
}
