package org.sonatype.nexus.repository.pypi.upgrade;

import java.io.File;
import java.io.IOException;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PyPiUpgrade_1_2Test
    extends TestSupport
{
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Mock
  private ApplicationDirectories applicationDirectories;

  private PyPiUpgrade_1_2 underTest;

  @Before
  public void setup() throws IOException {
    when(applicationDirectories.getWorkDirectory("db")).thenReturn(tempFolder.newFolder());
    underTest = new PyPiUpgrade_1_2(applicationDirectories);
  }

  @Test
  public void testApply() throws Exception {
    underTest.apply();

    File markerFile = new File(applicationDirectories.getWorkDirectory("db"), PyPiUpgrade_1_2.MARKER_FILE);
    assertTrue(markerFile.exists());
  }
}
