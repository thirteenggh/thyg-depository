package org.sonatype.nexus.repository.pypi.internal;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Search result for PyPI.
 *
 * @since 3.1
 */
public final class PyPiSearchResult
{
  private final String name;

  private final String version;

  private final String summary;

  public PyPiSearchResult(@Nonnull final String name, @Nonnull final String version, @Nonnull final String summary) {
    this.name = checkNotNull(name);
    this.version = checkNotNull(version);
    this.summary = checkNotNull(summary);
  }

  public String getName() {
    return name;
  }

  public String getVersion() {
    return version;
  }

  public String getSummary() {
    return summary;
  }
}
