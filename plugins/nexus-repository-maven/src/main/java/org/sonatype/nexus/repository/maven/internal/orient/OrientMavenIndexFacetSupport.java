package org.sonatype.nexus.repository.maven.internal.orient;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.transaction.UnitOfWork;

import org.joda.time.DateTime;

/**
 * {@link MavenIndexFacet} support.
 *
 * @since 3.0
 */
public abstract class OrientMavenIndexFacetSupport
    extends FacetSupport
    implements MavenIndexFacet
{
  final MavenIndexPublisher mavenIndexPublisher;

  OrientMavenIndexFacetSupport(final MavenIndexPublisher mavenIndexPublisher) {
    this.mavenIndexPublisher = mavenIndexPublisher;
  }

  @Nullable
  public DateTime lastPublished() throws IOException {
    UnitOfWork.begin(getRepository().facet(StorageFacet.class).txSupplier());
    try {
      return mavenIndexPublisher.lastPublished(getRepository());
    }
    finally {
      UnitOfWork.end();
    }
  }

  @Override
  public void unpublishIndex() throws IOException {
    UnitOfWork.begin(getRepository().facet(StorageFacet.class).txSupplier());
    try {
      mavenIndexPublisher.unpublishIndexFiles(getRepository());
    }
    finally {
      UnitOfWork.end();
    }
  }
}
