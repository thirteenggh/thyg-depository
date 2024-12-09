package org.sonatype.nexus.repository.maven.internal.hosted;

import java.io.IOException;

import org.sonatype.nexus.repository.maven.MavenIndexFacet;

public interface MavenHostedIndexFacet
    extends MavenIndexFacet
{
  void publishIndex() throws IOException;
}
