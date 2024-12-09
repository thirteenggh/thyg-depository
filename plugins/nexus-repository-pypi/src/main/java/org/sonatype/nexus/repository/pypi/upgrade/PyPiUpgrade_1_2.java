package org.sonatype.nexus.repository.pypi.upgrade;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.app.ApplicationDirectories;
import org.sonatype.nexus.common.upgrade.DependsOn;
import org.sonatype.nexus.common.upgrade.Upgrades;
import org.sonatype.nexus.orient.DatabaseInstanceNames;
import org.sonatype.nexus.orient.DatabaseUpgradeSupport;

/**
 * Create a marker file that will trigger a task to compensate for the change
 * in proxy asset names by deleting all PyPi proxy assets that start with "packages/".
 *
 * @since 3.22
 */
@Named
@Singleton
@Upgrades(model = PyPiModel.NAME, from = "1.1", to = "1.2")
@DependsOn(model = DatabaseInstanceNames.COMPONENT, version = "1.14", checkpoint = true)
@DependsOn(model = DatabaseInstanceNames.CONFIG, version = "1.8")
public class PyPiUpgrade_1_2
    extends DatabaseUpgradeSupport
{
  public static final String MARKER_FILE = PyPiUpgrade_1_2.class.getSimpleName() + ".marker";

  private Path markerFile;

  @Inject
  public PyPiUpgrade_1_2(final ApplicationDirectories directories)
  {
    this.markerFile = new File(directories.getWorkDirectory("db"), MARKER_FILE).toPath();
  }

  @Override
  public void apply() throws Exception {
    if (!Files.exists(markerFile)) {
      Files.createFile(markerFile);
    }
  }
}
