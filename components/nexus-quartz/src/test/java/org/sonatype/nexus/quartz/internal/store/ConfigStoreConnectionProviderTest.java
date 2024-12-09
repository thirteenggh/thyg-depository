package org.sonatype.nexus.quartz.internal.store;

import java.sql.Connection;
import java.sql.SQLException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.content.testsuite.groups.SQLTestGroup;
import org.sonatype.nexus.testdb.DataSessionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test the {@link ConfigStoreConnectionProvider}.
 */
@Category(SQLTestGroup.class)
public class ConfigStoreConnectionProviderTest
    extends TestSupport
{
  @Rule
  public DataSessionRule sessionRule = new DataSessionRule();

  private ConfigStoreConnectionProvider underTest;

  @Before
  public void setUp() {
    underTest = new ConfigStoreConnectionProvider(sessionRule);
    underTest.initialize();
  }

  @After
  public void tearDown() {
    underTest.shutdown();
    underTest = null;
  }

  @Test
  public void testCanOpenConnection() throws SQLException {
    try (Connection connection = underTest.getConnection()) {
      assertThat(connection.getMetaData().getURL(), is("jdbc:h2:mem:config"));
    }
  }
}
