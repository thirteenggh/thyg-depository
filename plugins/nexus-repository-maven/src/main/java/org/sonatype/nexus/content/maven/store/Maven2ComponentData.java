
package org.sonatype.nexus.content.maven.store;

import org.sonatype.nexus.repository.content.store.ComponentData;

/**
 * @since 3.29
 */
public class Maven2ComponentData
    extends ComponentData
{
  private String baseVersion;

  public String getBaseVersion() {
    return baseVersion;
  }

  public void setBaseVersion(final String baseVersion) {
    this.baseVersion = baseVersion;
  }
}
