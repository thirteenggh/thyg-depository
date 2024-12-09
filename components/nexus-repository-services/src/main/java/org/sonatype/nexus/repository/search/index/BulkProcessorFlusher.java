package org.sonatype.nexus.repository.search.index;

import java.util.concurrent.Callable;

import org.sonatype.goodies.common.ComponentSupport;

import org.elasticsearch.action.bulk.BulkProcessor;

/**
 * A Runnable for flushing a {@link BulkProcessor}.
 *
 * The intention is for this Runnable to be run on a single dedicated worker thread so that only that thread
 * is contesting for the BulkProcessor's MutEx when {@link BulkProcessor#flush()} is invoked.
 * That worker thread should be the same worker thread running instances of {@link BulkProcessorUpdater}.
 * This is achieved by using a single thread pool in {@link SearchIndexServiceImpl} to run instances of
 * this Runnable which are created in {@link SearchIndexServiceImpl}.
 *
 * @since 3.22
 */
public class BulkProcessorFlusher
    extends ComponentSupport
    implements Callable<Void>
{
  private final BulkProcessor bulkProcessor;

  public BulkProcessorFlusher(final BulkProcessor bulkProcessor) {
    this.bulkProcessor = bulkProcessor;
  }

  @Override
  public Void call() {
    log.debug("Trying to flush indexes ...");
    bulkProcessor.flush();
    log.debug("Finished flushing indexes.");
    return null;
  }
}
