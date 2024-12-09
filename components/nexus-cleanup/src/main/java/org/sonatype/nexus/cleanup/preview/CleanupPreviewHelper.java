package org.sonatype.nexus.cleanup.preview;

import org.sonatype.nexus.cleanup.storage.CleanupPolicyPreviewXO;
import org.sonatype.nexus.extdirect.model.PagedResponse;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.query.QueryOptions;
import org.sonatype.nexus.repository.rest.api.ComponentXO;

/**
 * Cleanup preview helper.
 *
 * @since 3.24
 */
public interface CleanupPreviewHelper
{
  PagedResponse<ComponentXO> getSearchResults(CleanupPolicyPreviewXO previewXO,
                                              Repository repository,
                                              QueryOptions queryOptions);
}
