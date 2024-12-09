package org.sonatype.nexus.internal.app;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.io.DirectoryHelper;

import com.google.common.base.Throwables;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link ApplicationDirectories} implementation.
 *
 * @since 2.8
 */
@Named
@Singleton
public class ApplicationDirectoriesImpl
  extends ComponentSupport
  implements ApplicationDirectories
{
  private final File installDir;

  private final File configDir;

  private final File workDir;

  private final File tempDir;

  @Inject
  public ApplicationDirectoriesImpl(@Named("${karaf.base}") final File installDir,
                                    @Named("${karaf.data}") final File workDir)
  {
    this.installDir = resolve(installDir, false);
    log.debug("Install dir: {}", this.installDir);

    this.configDir = resolve(new File(installDir, "etc"), false);
    log.debug("Config dir: {}", this.configDir);

    this.workDir = resolve(workDir, true);
    log.debug("Work dir: {}", this.workDir);

    // Resolve the tmp dir from system properties.
    String tmplocation = System.getProperty("java.io.tmpdir", "tmp");
    this.tempDir = resolve(new File(tmplocation), true);
    log.debug("Temp dir: {}", this.tempDir);
  }

  @Override
  public File getInstallDirectory() {
    return installDir;
  }

  @Override
  public File getConfigDirectory(final String subsystem) {
    checkNotNull(subsystem);
    return new File(configDir, subsystem);
  }

  @Override
  public File getTemporaryDirectory() {
    return tempDir;
  }

  @Override
  public File getWorkDirectory() {
    return workDir;
  }

  @Override
  public File getWorkDirectory(final String path, final boolean create) {
    checkNotNull(path);
    File dir = new File(path);
    if (!dir.isAbsolute()) {
      dir = new File(getWorkDirectory(), path);
    }
    return resolve(dir, create);
  }

  @Override
  public File getWorkDirectory(final String path) {
    return getWorkDirectory(path, true);
  }

  private void mkdir(final File dir) {
    if (dir.isDirectory()) {
      // skip already exists
      return;
    }

    try {
      DirectoryHelper.mkdir(dir.toPath());
      log.debug("Created directory: {}", dir);
    }
    catch (Exception e) {
      log.error("Failed to create directory: {}", dir);
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
  }

  private File resolve(File dir, final boolean create) {
    checkNotNull(dir);

    log.trace("Resolving directory: {}; create: {}", dir, create);
    try {
      dir = dir.getCanonicalFile();
    }
    catch (Exception e) {
      log.error("Failed to canonicalize directory: {}", dir);
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }

    if (create) {
      mkdir(dir);
    }

    return dir;
  }
}
