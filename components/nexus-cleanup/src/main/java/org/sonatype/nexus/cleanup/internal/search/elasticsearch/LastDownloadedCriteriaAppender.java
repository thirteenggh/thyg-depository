package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import javax.inject.Named;

import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.LAST_DOWNLOADED_KEY;

/**
 * Appends criteria for querying on lastDownloaded.
 *
 * @since 3.14
 */
@Named(LAST_DOWNLOADED_KEY)
public class LastDownloadedCriteriaAppender
    extends LessThanTimeOrNeverDownloadedCriteriaAppender
{
  public LastDownloadedCriteriaAppender() {
    super(LAST_DOWNLOADED_KEY);
  }
}
