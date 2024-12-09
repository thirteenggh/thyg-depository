package org.sonatype.nexus.repository.npm.internal.audit.report;

import java.util.List;
import java.util.Objects;

/**
 * Model will be serialized into Json representation for npm audit report.
 *
 * @since 3.24
 */
public class Finding
{
  private final String version;

  private final List<String> paths;

  public Finding(final String version, final List<String> paths) {
    this.version = version;
    this.paths = paths;
  }

  public String getVersion() {
    return version;
  }

  public List<String> getPaths() {
    return paths;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Finding finding = (Finding) o;
    return Objects.equals(version, finding.version) &&
        Objects.equals(paths, finding.paths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, paths);
  }
}
