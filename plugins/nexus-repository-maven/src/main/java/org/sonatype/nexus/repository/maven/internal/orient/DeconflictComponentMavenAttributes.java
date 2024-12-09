package org.sonatype.nexus.repository.maven.internal.orient;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.orient.entity.ConflictState;
import org.sonatype.nexus.orient.entity.DeconflictStepSupport;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.storage.Component;

import com.orientechnologies.orient.core.record.impl.ODocument;

import static org.sonatype.nexus.orient.entity.ConflictState.ALLOW;
import static org.sonatype.nexus.orient.entity.ConflictState.IGNORE;
import static org.sonatype.nexus.orient.entity.ConflictState.MERGE;
import static org.sonatype.nexus.repository.maven.internal.Attributes.P_PACKAGING;
import static org.sonatype.nexus.repository.storage.MetadataNodeEntityAdapter.P_ATTRIBUTES;

/**
 * Deconflicts component maven attributes:
 *
 * "maven2.packaging" - optional attribute, non-null wins over null
 *
 * @since 3.14
 */
@Named
@Singleton
public class DeconflictComponentMavenAttributes
    extends DeconflictStepSupport<Component>
{
  private static final String MAVEN_ATTRIBUTES = P_ATTRIBUTES + '.' + Maven2Format.NAME;

  private static final String PACKAGING_ATTRIBUTE = MAVEN_ATTRIBUTES + '.' + P_PACKAGING;

  @Override
  public ConflictState deconflict(final ODocument storedRecord, final ODocument changeRecord) {
    Object storedMaven = storedRecord.rawField(MAVEN_ATTRIBUTES);
    Object changeMaven = changeRecord.rawField(MAVEN_ATTRIBUTES);
    if (storedMaven != null && changeMaven != null) {
      // packaging is only added when the pom asset is fetched/deployed
      return pickNonNull(storedRecord, changeRecord, PACKAGING_ATTRIBUTE);
    }
    else if (changeMaven != null) {
      storedRecord.field(MAVEN_ATTRIBUTES, changeMaven);
      return ALLOW;
    }
    else if (storedMaven != null) {
      changeRecord.field(MAVEN_ATTRIBUTES, storedMaven);
      return MERGE;
    }
    return IGNORE;
  }
}
