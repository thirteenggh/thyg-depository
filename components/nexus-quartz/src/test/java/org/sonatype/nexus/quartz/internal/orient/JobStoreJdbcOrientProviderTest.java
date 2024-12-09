package org.sonatype.nexus.quartz.internal.orient;

import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;
import org.sonatype.nexus.quartz.internal.AbstractJobStoreTest;
import org.sonatype.nexus.quartz.internal.JobStoreJdbcProvider;

import org.junit.Rule;
import org.quartz.spi.JobStore;

public class JobStoreJdbcOrientProviderTest
    extends AbstractJobStoreTest
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("config");

  private JobStore jobStore;

  @Override
  protected JobStore createJobStore(String name) {
    jobStore = new JobStoreJdbcProvider(new ConfigOrientConnectionProvider(database.getInstanceProvider())).get();
    jobStore.setInstanceId("SINGLE_NODE_TEST");
    jobStore.setInstanceName(name);

    return jobStore;
  }

  @Override
  protected void destroyJobStore(String name) {
    jobStore.shutdown();
    jobStore = null;
  }
}
