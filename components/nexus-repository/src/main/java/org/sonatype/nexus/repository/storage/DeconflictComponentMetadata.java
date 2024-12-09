package org.sonatype.nexus.repository.storage;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.entity.ConflictState;
import org.sonatype.nexus.orient.entity.DeconflictStepSupport;

import com.orientechnologies.orient.core.record.impl.ODocument;

import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_LAST_UPDATED;

/**
 * Deconflicts component metadata:
 *
 * "last_updated" - book-keeping attribute, we're only interested in the latest time
 *
 * @since 3.14
 */
@Named
@Singleton
public class DeconflictComponentMetadata
    extends DeconflictStepSupport<Component>
{
  @Override
  public ConflictState deconflict(final ODocument storedRecord, final ODocument changeRecord) {
    return pickLatest(storedRecord, changeRecord, P_LAST_UPDATED);
  }
}
