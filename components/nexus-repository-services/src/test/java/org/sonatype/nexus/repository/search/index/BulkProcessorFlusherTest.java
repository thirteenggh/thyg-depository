package org.sonatype.nexus.repository.search.index;

import org.sonatype.goodies.testsupport.TestSupport;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class BulkProcessorFlusherTest
    extends TestSupport
{

  @Mock
  private BulkProcessor bulkProcessor;

  @InjectMocks
  private BulkProcessorFlusher underTest;

  @Test
  public void runShouldFlushBulkProcessor() {
    underTest.call();

    verify(bulkProcessor).flush();
  }
}
