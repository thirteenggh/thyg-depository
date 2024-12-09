package org.sonatype.nexus.repository.maven.internal.filter;

import org.sonatype.nexus.common.app.ApplicationDirectories;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class DiskBackedDuplicateDetectionStrategyTest
    extends DuplicateDetectionStrategyTestSupport
{
  @Rule
  public TemporaryFolder tmpDir = new TemporaryFolder();

  @Mock
  private ApplicationDirectories applicationDirectories;

  @Test
  public void shouldIdentifyDuplicates() throws Exception {
    when(applicationDirectories.getTemporaryDirectory()).thenReturn(tmpDir.getRoot());

    verifyDuplicateDetection(new DiskBackedDuplicateDetectionStrategy(applicationDirectories, 1, 10));
  }
}
