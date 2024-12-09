package org.sonatype.nexus.repository.maven.internal.filter;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;

import org.apache.maven.index.reader.Record;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class DuplicateDetectionStrategyProviderTest
    extends TestSupport
{
  private static final int MAX_HEAP_GB = 1;

  private static final int MAX_DISK_SIZE_GB = 10;

  @Rule
  public TemporaryFolder tmpDir = new TemporaryFolder();

  @Mock
  private ApplicationDirectories applicationDirectories;

  @Before
  public void setup() {
    when(applicationDirectories.getTemporaryDirectory()).thenReturn(tmpDir.getRoot());
  }

  @Test
  public void shouldReturnBloomStrategy() throws Exception {
    DuplicateDetectionStrategy<Record> strategy = new DuplicateDetectionStrategyProvider(applicationDirectories,
        "BLOOM", MAX_HEAP_GB, MAX_DISK_SIZE_GB).get();

    assertThat(strategy, is(instanceOf(BloomFilterDuplicateDetectionStrategy.class)));

    DuplicateDetectionStrategy<Record> strategyLowerCase = new DuplicateDetectionStrategyProvider(
        applicationDirectories, "bloom", MAX_HEAP_GB, MAX_DISK_SIZE_GB).get();

    assertThat(strategyLowerCase, is(instanceOf(BloomFilterDuplicateDetectionStrategy.class)));
  }

  @Test
  public void shouldReturnDiskStrategy() throws Exception {
    DuplicateDetectionStrategy<Record> strategy = new DuplicateDetectionStrategyProvider(applicationDirectories, "DISK",
        MAX_HEAP_GB, MAX_DISK_SIZE_GB)
        .get();

    assertThat(strategy, is(instanceOf(DiskBackedDuplicateDetectionStrategy.class)));

    DuplicateDetectionStrategy<Record> strategyLowerCase = new DuplicateDetectionStrategyProvider(
        applicationDirectories, "disk", MAX_HEAP_GB, MAX_DISK_SIZE_GB)
        .get();

    assertThat(strategyLowerCase, is(instanceOf(DiskBackedDuplicateDetectionStrategy.class)));
  }

  @Test
  public void shouldReturnInMemoryStrategy() throws Exception {
    DuplicateDetectionStrategy<Record> strategy = new DuplicateDetectionStrategyProvider(applicationDirectories, "HASH",
        MAX_HEAP_GB, MAX_DISK_SIZE_GB)
        .get();

    assertThat(strategy, is(instanceOf(HashBasedDuplicateDetectionStrategy.class)));

    DuplicateDetectionStrategy<Record> strategyLowerCase = new DuplicateDetectionStrategyProvider(
        applicationDirectories, "hash", MAX_HEAP_GB, MAX_DISK_SIZE_GB)
        .get();

    assertThat(strategyLowerCase, is(instanceOf(HashBasedDuplicateDetectionStrategy.class)));
  }

  @Test
  public void shouldFallBackToBloomForUnknownStrategy() throws Exception {
    DuplicateDetectionStrategy<Record> strategy = new DuplicateDetectionStrategyProvider(applicationDirectories,
        "unknown", MAX_HEAP_GB, MAX_DISK_SIZE_GB)
        .get();

    assertThat(strategy, is(instanceOf(BloomFilterDuplicateDetectionStrategy.class)));
  }
}
