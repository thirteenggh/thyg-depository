package org.sonatype.nexus.quartz.internal.store;

import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup;
import org.sonatype.nexus.quartz.internal.AbstractJobStoreTest;
import org.sonatype.nexus.quartz.internal.JobStoreJdbcProvider;
import org.sonatype.nexus.quartz.internal.QuartzDAO;
import org.sonatype.nexus.testdb.DataSessionRule;

import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.quartz.spi.JobStore;

@Category(SQLTestGroup.class)
public class JobStoreJdbcStoreProviderTest
    extends AbstractJobStoreTest
{
  @Rule
  public DataSessionRule sessionRule = new DataSessionRule().access(QuartzDAO.class);

  private JobStore jobStore;

  @Override
  protected JobStore createJobStore(final String name) {
    jobStore = new JobStoreJdbcProvider(new ConfigStoreConnectionProvider(sessionRule)).get();
    jobStore.setInstanceId("SINGLE_NODE_TEST");
    jobStore.setInstanceName(name);

    return jobStore;
  }

  @Override
  protected void destroyJobStore(final String name) {
    jobStore.shutdown();
    jobStore = null;
  }
}
