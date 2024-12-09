package org.sonatype.nexus.repository.maven.internal.orient;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategy;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategyProvider;
import org.sonatype.nexus.repository.maven.internal.hosted.MavenHostedIndexFacet;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.transaction.UnitOfWork;

import org.apache.maven.index.reader.Record;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Hosted implementation of {@link MavenIndexFacet}.
 *
 * @since 3.0
 */
@Named
@Priority(Integer.MAX_VALUE)
public class OrientMavenHostedIndexFacet
    extends OrientMavenIndexFacetSupport
    implements MavenHostedIndexFacet
{
  private final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider;

  @Inject
  public OrientMavenHostedIndexFacet(
      final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider,
      final MavenIndexPublisher mavenIndexPublisher)
  {
    super(mavenIndexPublisher);
    this.duplicateDetectionStrategyProvider = checkNotNull(duplicateDetectionStrategyProvider);
  }

  @Override
  public void publishIndex() throws IOException {
    UnitOfWork.begin(getRepository().facet(StorageFacet.class).txSupplier());
    try (DuplicateDetectionStrategy<Record> strategy = duplicateDetectionStrategyProvider.get()) {
      mavenIndexPublisher.publishHostedIndex(getRepository(), strategy);
    }
    finally {
      UnitOfWork.end();
    }
  }
}
