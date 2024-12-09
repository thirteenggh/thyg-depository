package org.sonatype.nexus.repository.attributes.internal;

import javax.inject.Named;

import org.sonatype.nexus.common.collect.ImmutableNestedAttributesMap;
import org.sonatype.nexus.common.collect.NestedAttributesMap;
import org.sonatype.nexus.repository.AttributeChange;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.attributes.AttributesFacet;
import org.sonatype.nexus.repository.storage.Bucket;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.storage.StorageTx;
import org.sonatype.nexus.repository.transaction.TransactionalStoreMetadata;
import org.sonatype.nexus.transaction.Transactional;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * Persists repository attributes in the repository's corresponding {@link Bucket}.
 *
 * @since 3.0
 */
@Named
public class AttributesFacetImpl
    extends FacetSupport
    implements AttributesFacet
{
  @Override
  public ImmutableNestedAttributesMap getAttributes() {
    return Transactional.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
      final StorageTx tx = UnitOfWork.currentTx();
      final NestedAttributesMap attributes = tx.findBucket(getRepository()).attributes();
      return new ImmutableNestedAttributesMap(null, attributes.getKey(), attributes.backing());
    });
  }

  @Override
  public void modifyAttributes(final AttributeChange change) {
    TransactionalStoreMetadata.operation.withDb(facet(StorageFacet.class).txSupplier()).call(() -> {
      final StorageTx tx = UnitOfWork.currentTx();

      final Bucket bucket = tx.findBucket(getRepository());
      change.apply(bucket.attributes());
      tx.saveBucket(bucket);

      return null;
    });
  }
}
