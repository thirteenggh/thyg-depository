package org.sonatype.nexus.repository.maven.internal.filter;

import org.junit.Test;

public class HashBasedDuplicateDetectionStrategyTest
    extends DuplicateDetectionStrategyTestSupport
{
  @Test
  public void shouldIdentifyDuplicates() throws Exception {
    verifyDuplicateDetection(new HashBasedDuplicateDetectionStrategy());
  }
}
