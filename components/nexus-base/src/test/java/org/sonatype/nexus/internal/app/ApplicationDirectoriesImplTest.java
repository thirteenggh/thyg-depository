package org.sonatype.nexus.internal.app;

import java.io.File;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.goodies.testsupport.hamcrest.FileMatchers;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Tests for {@link ApplicationDirectoriesImpl}
 */
public class ApplicationDirectoriesImplTest
  extends TestSupport
{
  private File installDir;

  private File workDir;

  private ApplicationDirectoriesImpl underTest;

  @Before
  public void setUp() throws Exception {
    installDir = util.createTempDir("install");
    workDir = util.createTempDir("work");
    underTest = new ApplicationDirectoriesImpl(installDir, workDir);
  }

  @Test
  public void ensureTempDir_exists() throws Exception {
    File dir = underTest.getTemporaryDirectory();
    assertThat(dir, notNullValue());
  }

  @Test
  public void ensureWorkDir_exists() throws Exception {
    File dir = underTest.getWorkDirectory();
    assertThat(dir, notNullValue());
    assertThat(dir, is(workDir));
    assertThat(dir, FileMatchers.exists());
  }

  @Test
  public void ensureWorkDir_childExists() throws Exception {
    File dir = underTest.getWorkDirectory("child");
    assertThat(dir, notNullValue());
    assertThat(dir, FileMatchers.exists());
  }

  @Test
  public void ensureWorkDir_childWithCreateExists() throws Exception {
    File dir = underTest.getWorkDirectory("child", true);
    assertThat(dir, notNullValue());
    assertThat(dir, FileMatchers.exists());
  }

  @Test
  public void ensureWorkDir_childNoCreateNotExists() throws Exception {
    File dir = underTest.getWorkDirectory("child", false);
    assertThat(dir, notNullValue());
    assertThat(dir, not(FileMatchers.exists()));
  }

  @Test
  public void ensureWorkDir_referencesSonatypeWorkFolderUnlessAbsolute() throws Exception {
    File tempDir = util.createTempDir("temp");

    File relative = underTest.getWorkDirectory(".");
    File absolute = underTest.getWorkDirectory(tempDir.getAbsolutePath());
    assertThat(relative.getCanonicalFile(), equalTo(workDir.getCanonicalFile()));
    assertThat(absolute.getCanonicalFile(), equalTo(tempDir.getCanonicalFile()));
  }
}
