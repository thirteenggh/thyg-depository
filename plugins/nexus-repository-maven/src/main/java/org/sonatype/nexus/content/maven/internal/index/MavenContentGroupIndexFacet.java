package org.sonatype.nexus.content.maven.internal.index;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategy;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategyProvider;

import org.apache.maven.index.reader.Record;

/**
 * Group implementation of {@link MavenIndexFacet}.
 *
 * @since 3.26
 */
@Named
public class MavenContentGroupIndexFacet
    extends MavenContentIndexFacetSupport
{
  private final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider;

  @Inject
  public MavenContentGroupIndexFacet(
      final MavenIndexPublisher mavenIndexPublisher,
      final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider) {
    super(mavenIndexPublisher);
    this.duplicateDetectionStrategyProvider = duplicateDetectionStrategyProvider;
  }

  @Override
  public void publishIndex() throws IOException {
    try (DuplicateDetectionStrategy<Record> strategy = duplicateDetectionStrategyProvider.get()) {
      mavenIndexPublisher.publishGroupIndex(getRepository(), facet(GroupFacet.class).leafMembers(), strategy);
    }
  }
}
