package org.sonatype.nexus.repository.apt.internal.proxy;

import java.io.IOException;
import java.util.List;

import javax.inject.Named;

import org.sonatype.nexus.repository.apt.internal.snapshot.AptSnapshotFacet;
import org.sonatype.nexus.repository.apt.internal.snapshot.AptSnapshotFacetSupport;
import org.sonatype.nexus.repository.apt.internal.snapshot.SnapshotItem;
import org.sonatype.nexus.repository.apt.internal.snapshot.SnapshotItem.ContentSpecifier;

/**
 * @since 3.17
 */
@Named
public class AptProxySnapshotFacet
    extends AptSnapshotFacetSupport
    implements AptSnapshotFacet
{
  @Override
  protected List<SnapshotItem> fetchSnapshotItems(final List<ContentSpecifier> specs) throws IOException {
    AptProxyFacet proxyFacet = getRepository().facet(AptProxyFacet.class);
    return proxyFacet.getSnapshotItems(specs);
  }
}
