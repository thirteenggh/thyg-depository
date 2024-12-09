package org.sonatype.nexus.repository.npm.internal.audit.report;

import java.util.List;
import java.util.Objects;

/**
 * Model will be serialized into Json representation for npm audit report.
 *
 * @since 3.24
 */
public class Resolve
{
  private final int id;

  private final transient List<String> pathList;

  private final String path;

  private final boolean dev;

  private final boolean optional;

  private final boolean bundled;

  public Resolve(
      final int id,
      final List<String> pathList,
      final String path,
      final boolean dev,
      final boolean optional,
      final boolean bundled)
  {
    this.id = id;
    this.pathList = pathList;
    this.path = path;
    this.dev = dev;
    this.optional = optional;
    this.bundled = bundled;
  }

  public int getId() {
    return id;
  }

  public List<String> getPathList() {
    return pathList;
  }

  public String getPath() {
    return path;
  }

  public boolean isDev() {
    return dev;
  }

  public boolean isOptional() {
    return optional;
  }

  public boolean isBundled() {
    return bundled;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Resolve resolve = (Resolve) o;
    return id == resolve.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
