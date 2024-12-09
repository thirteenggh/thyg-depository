package org.sonatype.nexus.repository.storage;

import javax.annotation.Nullable;

import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_GROUP;
import static org.sonatype.nexus.repository.storage.ComponentEntityAdapter.P_VERSION;

/**
 * @since 3.7
 */
public class DefaultComponent
    extends AbstractMetadataNode<Component>
    implements Component
{
  private String group;

  private String version;

  /**
   * Gets the group or {@code null} if undefined.
   */
  @Nullable
  public String group() {
    return group;
  }

  /**
   * Gets the group or throws a runtime exception if undefined.
   */
  public String requireGroup() {
    return require(group, P_GROUP);
  }

  /**
   * Sets the group to the given value, or {@code null} to un-define it.
   */
  public DefaultComponent group(@Nullable final String group) {
    this.group = group;
    return this;
  }

  /**
   * Gets the version or {@code null} if undefined.
   */
  @Nullable
  public String version() {
    return version;
  }

  /**
   * Gets the version or throws a runtime exception if undefined.
   */
  public String requireVersion() {
    return require(version, P_VERSION);
  }

  /**
   * Sets the version to the given value, or {@code null} to un-define it.
   */
  public DefaultComponent version(@Nullable final String version) {
    this.version = version;
    return this;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
        "metadata=" + getEntityMetadata() +
        ", name=" + name() +
        ", version=" + version() +
        ", group=" + group() +
        '}';
  }

}
