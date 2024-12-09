package org.sonatype.nexus.content.maven.internal.index;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;

import org.joda.time.DateTime;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link MavenIndexFacet} support.
 *
 * @since 3.26
 */
public abstract class MavenContentIndexFacetSupport
    extends FacetSupport
    implements MavenIndexFacet
{

  final MavenIndexPublisher mavenIndexPublisher;

  protected MavenContentIndexFacetSupport(final MavenIndexPublisher mavenIndexPublisher) {
    this.mavenIndexPublisher = checkNotNull(mavenIndexPublisher);
  }

  @Nullable
  @Override
  public DateTime lastPublished() throws IOException {
    return mavenIndexPublisher.lastPublished(getRepository());
  }

  @Override
  public void unpublishIndex() throws IOException {
    mavenIndexPublisher.unpublishIndexFiles(getRepository());
  }
}
