package org.sonatype.nexus.repository.browse.internal;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.selector.internal.OrientContentAuthHelper;

import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.record.impl.ODocument;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A factory for creating an {@code asset} browsing {@link Iterable<ODocument>} using the injected
 * {@link OrientContentAuthHelper} and {@code pageSize}.
 *
 * @since 3.17
 */
@Named
@Singleton
public class BrowseAssetIterableFactory
    extends ComponentSupport
{
  private final OrientContentAuthHelper contentAuthHelper;

  private final int pageSize;

  @Inject
  public BrowseAssetIterableFactory(final OrientContentAuthHelper contentAuthHelper,
                                    @Named("${nexus.asset.browse.pageSize:-5000}") final int pageSize)
  {
    this.contentAuthHelper = checkNotNull(contentAuthHelper);
    this.pageSize = pageSize;
  }

  public Iterable<ODocument> create(final ODatabaseDocumentTx db,
                                    @Nullable final String rid,
                                    final String repositoryName,
                                    final List<String> bucketIds,
                                    final int limit)
  {
    return () -> new BrowseAssetIterator(contentAuthHelper, db, rid, repositoryName, bucketIds, limit, pageSize);
  }
}
