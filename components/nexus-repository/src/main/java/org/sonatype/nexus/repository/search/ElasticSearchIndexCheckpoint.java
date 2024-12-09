package org.sonatype.nexus.repository.search;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.upgrade.Checkpoint;
import org.sonatype.nexus.common.upgrade.Checkpoints;

/**
 * Upgrade checkpoint for elasticsearch index. It exists for the sole purpose of forcing a reindexing of all
 * repositories to pull in new attributes/elements.
 *
 * @since 3.6.1
 */
@Named
@Singleton
@Checkpoints(model = ElasticSearchIndexCheckpoint.MODEL, local = true)
public class ElasticSearchIndexCheckpoint
    implements Checkpoint
{
  public static final String MODEL = "elasticsearch";

  @Override
  public void begin(final String version) throws Exception {
    //no-op
  }

  @Override
  public void commit() throws Exception {
    // no-op
  }

  @Override
  public void rollback() throws Exception {
    // no-op
  }

  @Override
  public void end() {
    //no-op
  }
}
