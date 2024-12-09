package org.sonatype.nexus.repository.npm.internal.audit.parser;

import java.util.Map;
import java.util.Objects;

/**
 * Node for parsing package-lock.json
 *
 * @since 3.24
 */
public class PackageLockNode
{
  private final String version;

  private final boolean dev;

  private final boolean optional;

  private String parentNodeName;

  private final Map<String, PackageLockNode> dependencies;

  public PackageLockNode(
      final String version,
      final boolean dev,
      final boolean optional,
      final String parentNodeName,
      final Map<String, PackageLockNode> dependencies)
  {
    this.version = version;
    this.dev = dev;
    this.optional = optional;
    this.parentNodeName = parentNodeName;
    this.dependencies = dependencies;
  }

  public Map<String, PackageLockNode> getDependencies() {
    return dependencies;
  }

  public String getVersion() {
    return version;
  }

  public boolean isDev() {
    return dev;
  }

  public boolean isOptional() {
    return optional;
  }

  public String getParentNodeName() {
    return parentNodeName;
  }

  public void setParentNodeName(String parentNodeName) {
    this.parentNodeName = parentNodeName;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PackageLockNode that = (PackageLockNode) o;
    return dev == that.dev &&
        optional == that.optional &&
        Objects.equals(version, that.version) &&
        Objects.equals(parentNodeName, that.parentNodeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, dev, optional, parentNodeName);
  }
}
