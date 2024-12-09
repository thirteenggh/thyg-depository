package org.sonatype.nexus.repository.apt.internal.snapshot;

import java.io.IOException;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.view.Content;

/**
 * @since 3.17
 */
@Facet.Exposed
public interface AptSnapshotFacet
    extends Facet
{
  boolean isSnapshotableFile(final String path);

  void createSnapshot(final String id, final SnapshotComponentSelector spec) throws IOException;

  @Nullable
  Content getSnapshotFile(final String id, final String path) throws IOException;

  void deleteSnapshot(final String id) throws IOException;
}
