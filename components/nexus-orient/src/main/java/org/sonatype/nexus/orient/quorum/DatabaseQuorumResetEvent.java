package org.sonatype.nexus.orient.quorum;

/**
 * Emitted when the write quorum in a cluster has been {@link DatabaseQuorumService#resetWriteQuorum() reset}.
 * 
 * @since 3.7
 */
public class DatabaseQuorumResetEvent
{
  @Override
  public String toString() {
    return getClass().getSimpleName();
  }
}
