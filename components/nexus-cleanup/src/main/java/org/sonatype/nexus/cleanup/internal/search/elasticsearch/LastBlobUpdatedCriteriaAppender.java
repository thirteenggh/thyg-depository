package org.sonatype.nexus.cleanup.internal.search.elasticsearch;

import javax.inject.Named;

import static org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer.LAST_BLOB_UPDATED_KEY;

/**
 * Appends criteria for querying on the most recent blob_updated date from a components assets.
 *
 * @since 3.14
 */
@Named(LAST_BLOB_UPDATED_KEY)
public class LastBlobUpdatedCriteriaAppender
    extends LessThanTimeCriteriaAppender
{
  public LastBlobUpdatedCriteriaAppender() {
    super(LAST_BLOB_UPDATED_KEY);
  }
}
