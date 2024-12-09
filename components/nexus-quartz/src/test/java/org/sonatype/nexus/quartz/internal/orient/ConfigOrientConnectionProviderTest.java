package org.sonatype.nexus.quartz.internal.orient;

import java.sql.Connection;
import java.sql.SQLException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.testsupport.DatabaseInstanceRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the {@link ConfigOrientConnectionProvider}.
 */
public class ConfigOrientConnectionProviderTest
    extends TestSupport
{
  @Rule
  public DatabaseInstanceRule database = DatabaseInstanceRule.inMemory("config");

  private ConfigOrientConnectionProvider underTest;

  @Before
  public void setUp() throws Exception {
    underTest = new ConfigOrientConnectionProvider(database.getInstanceProvider());
    underTest.initialize();
  }

  @After
  public void tearDown() throws Exception {
    underTest.shutdown();
    underTest = null;
  }

  @Test
  public void testCanOpenConnection() throws SQLException {
    try (Connection connection = underTest.getConnection()) {
      assertThat(connection.getMetaData().getURL(), is("memory:config"));
    }
  }
}
