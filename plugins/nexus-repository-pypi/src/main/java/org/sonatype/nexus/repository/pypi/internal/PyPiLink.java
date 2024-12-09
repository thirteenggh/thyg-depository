package org.sonatype.nexus.repository.pypi.internal;

import javax.annotation.Nonnull;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Container for URL PEP 503 compatible urls.
 *
 * @since 3.20
 */
public final class PyPiLink
{
  private final String file;

  private final String link;

  private final String dataRequiresPython;

  public PyPiLink(@Nonnull final String file,
           @Nonnull final String link,
           final String dataRequiresPython) {
    this.file = checkNotNull(file);
    this.link = checkNotNull(link);
    this.dataRequiresPython = dataRequiresPython != null ? dataRequiresPython : "";
  }

  public PyPiLink(final String file, final String link) {
    this(file, link, "");
  }

  public String getLink() {
    return link;
  }

  public String getFile() {
    return file;
  }

  public String getDataRequiresPython() {
    return dataRequiresPython;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PyPiLink pyPiLink = (PyPiLink) o;
    return Objects.equal(file, pyPiLink.file) &&
        Objects.equal(link, pyPiLink.link) &&
        Objects.equal(dataRequiresPython, pyPiLink.dataRequiresPython);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(file, link, dataRequiresPython);
  }
}
