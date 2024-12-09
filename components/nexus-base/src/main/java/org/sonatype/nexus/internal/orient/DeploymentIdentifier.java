package org.sonatype.nexus.internal.orient;

import org.sonatype.nexus.common.entity.AbstractEntity;

/**
 * Representation of a unique identifier for this NXRM deployment.
 *
 * {@link #getId()} is randomly generated at first launch for NXRM and is not modifiable.
 *
 * @since 3.6.1
 */
class DeploymentIdentifier
    extends AbstractEntity
{
  private String id;
  private String alias;

  public String getId() {
    return id;
  }

  DeploymentIdentifier setId(final String id) {
    this.id = id;
    return this;
  }

  public String getAlias() {
    return alias;
  }

  public DeploymentIdentifier setAlias(final String alias) {
    this.alias = alias;
    return this;
  }

  @Override
  public String toString() {
    return "DeploymentIdentifier{" +
        "id='" + id + '\'' +
        ", alias='" + alias + '\'' +
        '}';
  }
}
