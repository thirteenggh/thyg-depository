package org.sonatype.nexus.content.maven.internal.index;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategy;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategyProvider;
import org.sonatype.nexus.repository.maven.internal.hosted.MavenHostedIndexFacet;

import org.apache.maven.index.reader.Record;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Hosted implementation of {@link MavenIndexFacet}.
 *
 * @since 3.26
 */
@Named
public class MavenContentHostedIndexFacet
    extends MavenContentIndexFacetSupport
    implements MavenHostedIndexFacet
{
  private final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider;

  @Inject
  public MavenContentHostedIndexFacet(
      final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider,
      final MavenIndexPublisher mavenIndexPublisher)
  {
    super(mavenIndexPublisher);
    this.duplicateDetectionStrategyProvider = checkNotNull(duplicateDetectionStrategyProvider);
  }

  @Override
  public void publishIndex() throws IOException {
    try (DuplicateDetectionStrategy<Record> strategy = duplicateDetectionStrategyProvider.get()) {
      mavenIndexPublisher.publishHostedIndex(getRepository(), strategy);
    }
  }
}
