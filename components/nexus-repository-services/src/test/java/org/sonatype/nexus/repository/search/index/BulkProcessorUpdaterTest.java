package org.sonatype.nexus.repository.search.index;

import org.sonatype.goodies.testsupport.TestSupport;

import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class BulkProcessorUpdaterTest
    extends TestSupport
{
  @Mock
  private BulkProcessor bulkProcessor;

  @Mock
  private ActionRequest<DeleteRequest> deleteRequest;

  @InjectMocks
  private BulkProcessorUpdater<DeleteRequest> underTest;

  @Test
  public void runShouldAddRequestToBulkProcessor() {
    underTest.call();

    verify(bulkProcessor).add(deleteRequest);
  }
}
