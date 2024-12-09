package org.sonatype.nexus.repository.golang.internal.metadata;

/**
 * Object for storing Go specific attributes
 *
 * @since 3.17
 */
public final class GolangAttributes
{
  private String module;
  private String version;

  public String getModule() {
    return module;
  }

  public void setModule(final String module) {
    this.module = module;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }
}
