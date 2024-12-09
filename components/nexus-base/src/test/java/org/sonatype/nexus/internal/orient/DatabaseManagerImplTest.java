package org.sonatype.nexus.internal.orient;

import java.io.File;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.orient.DatabaseRestorer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link DatabaseManagerImpl}.
 */
public class DatabaseManagerImplTest
    extends TestSupport
{
  @Rule
  public TemporaryFolder tmpDir = new TemporaryFolder();

  private DatabaseManagerImpl databaseManager;

  @Before
  public void setUp() {
    databaseManager = new DatabaseManagerImpl(tmpDir.getRoot(), mock(DatabaseRestorer.class));
  }

  @Test
  public void testConnectionUri() throws Exception {
    String dbPath = new File(tmpDir.getRoot(), "dbName").getCanonicalPath().replace('\\', '/');
    assertThat(databaseManager.connectionUri("dbName"), is("plocal:" + dbPath));
  }
}
