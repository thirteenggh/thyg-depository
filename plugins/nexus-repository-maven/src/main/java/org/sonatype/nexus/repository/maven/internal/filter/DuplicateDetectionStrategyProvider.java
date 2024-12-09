package org.sonatype.nexus.repository.maven.internal.filter;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.apache.maven.index.reader.Record;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides the configured record duplicate detection strategy. Defaults to
 * {@link BloomFilterDuplicateDetectionStrategy}
 */
@Singleton
@Named
public class DuplicateDetectionStrategyProvider
    extends ComponentSupport
    implements Provider<DuplicateDetectionStrategy<Record>>
{
  private final String strategy;

  private final ApplicationDirectories applicationDirectories;

  private final int maxHeapGb;

  private final int maxDiskSizeGb;

  @Inject
  public DuplicateDetectionStrategyProvider(
      final ApplicationDirectories applicationDirectories,
      @Named("${nexus.maven.duplicate.detection.strategy:-BLOOM}") final String strategy,
      @Named("${nexus.maven.duplicate.detection.heap.max.gb:-1}") final int maxHeapGb,
      @Named("${nexus.maven.duplicate.detection.disk.max.gb:-10}") final int maxDiskSizeGb)
  {
    this.applicationDirectories = checkNotNull(applicationDirectories);
    this.strategy = checkNotNull(strategy);
    this.maxHeapGb = maxHeapGb;
    this.maxDiskSizeGb = maxDiskSizeGb;
  }

  @Override
  public DuplicateDetectionStrategy<Record> get() {
    switch (getStrategy()) {
      case HASH:
        return new HashBasedDuplicateDetectionStrategy();
      case DISK:
        return new DiskBackedDuplicateDetectionStrategy(applicationDirectories, maxHeapGb, maxDiskSizeGb);
      case BLOOM:
      default:
        return new BloomFilterDuplicateDetectionStrategy();
    }
  }

  private Strategy getStrategy() {
    Strategy duplicateStrategy = null;
    try {
      duplicateStrategy = Strategy.valueOf(this.strategy.toUpperCase());
    }
    catch (IllegalArgumentException e) { // NOSONAR
      log.warn("Unsupported record duplicate detection strategy {}. Falling back to bloom. ", duplicateStrategy);
      duplicateStrategy = Strategy.BLOOM;
    }
    return duplicateStrategy;
  }

  private enum Strategy
  {
    HASH,
    BLOOM,
    DISK
  }
}
