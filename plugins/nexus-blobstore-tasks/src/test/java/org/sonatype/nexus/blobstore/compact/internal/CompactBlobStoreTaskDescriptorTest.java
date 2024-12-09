package org.sonatype.nexus.blobstore.compact.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.scheduling.TaskConfiguration;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.sonatype.nexus.scheduling.TaskDescriptorSupport.MULTINODE_KEY;

public class CompactBlobStoreTaskDescriptorTest
    extends TestSupport
{
  CompactBlobStoreTaskDescriptor underTest;

  TaskConfiguration taskConfiguration = new TaskConfiguration();

  @Before
  public void setUp() throws Exception {
    underTest = new CompactBlobStoreTaskDescriptor();
  }

  @Test
  public void initializeConfiguration() throws Exception {
    underTest.initializeConfiguration(taskConfiguration);
    assertThat(taskConfiguration.getBoolean(MULTINODE_KEY, false), is(true));
  }
}
