package org.sonatype.nexus.repository.npm.internal.audit.parser;

import java.util.Map;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * Root node for parsing package-lock.json
 *
 * @since 3.24
 */
public class RootPackageLockNode
    extends PackageLockNode
{
  /**
   * Used only for project name in root node. Dependency name stored as key in map dependencies.
   */
  private String name;

  /**
   * Used only for application id in root node.
   */
  @SerializedName("APP_ID")
  private String applicationId;

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(final String applicationId) {
    this.applicationId = applicationId;
  }

  public RootPackageLockNode(
      final String version,
      final boolean dev,
      final boolean optional,
      final String parentNodeName,
      final Map<String, PackageLockNode> dependencies,
      final String name,
      final String applicationId)
  {
    super(version, dev, optional, parentNodeName, dependencies);
    this.name = name;
    this.applicationId = applicationId;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    RootPackageLockNode that = (RootPackageLockNode) o;
    return Objects.equals(name, that.name) &&
        Objects.equals(applicationId, that.applicationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name, applicationId);
  }
}
