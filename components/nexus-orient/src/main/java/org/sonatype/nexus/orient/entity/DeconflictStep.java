package org.sonatype.nexus.orient.entity;

import org.sonatype.nexus.common.entity.Entity;

import com.orientechnologies.orient.core.record.impl.ODocument;

/**
 * Represents a specific step in deconflicting differences between entity records.
 *
 * @since 3.14
 */
public interface DeconflictStep<T extends Entity>
{
  /**
   * Attempts to deconflict differences between specific parts of the given records.
   * This may result in either record changing in-memory as differences are resolved
   * depending which record is seen as having the authoritative content for this step.
   *
   * The returned {@link ConflictState} should reflect which records were changed.
   *
   * For example: if the incoming change was seen as authoritative (ie. its data was
   * folded into the stored record) you'd return ALLOW. Whereas if the stored record
   * was authoritative (ie. it was merged into the incoming change) return MERGE.
   *
   * If it's obvious that deconfliction cannot proceed then you should return DENY.
   */
  ConflictState deconflict(ODocument storedRecord, ODocument changeRecord);
}
