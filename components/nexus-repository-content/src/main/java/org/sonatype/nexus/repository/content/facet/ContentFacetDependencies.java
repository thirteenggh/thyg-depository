package org.sonatype.nexus.repository.content.facet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.datastore.api.DataSessionSupplier;
import org.sonatype.nexus.security.ClientInfoProvider;
import org.sonatype.nexus.validation.ConstraintViolationFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Internal dependencies injected into a repository's {@link ContentFacet}.
 *
 * @since 3.24
 */
@Named
@Singleton
class ContentFacetDependencies
{
  final BlobStoreManager blobStoreManager;

  final DataSessionSupplier dataSessionSupplier;

  final ConstraintViolationFactory constraintViolationFactory;

  final ClientInfoProvider clientInfoProvider;

  final NodeAccess nodeAccess;

  final AssetBlobValidators assetBlobValidators;

  @Inject
  public ContentFacetDependencies(final BlobStoreManager blobStoreManager,
                                  final DataSessionSupplier dataSessionSupplier,
                                  final ConstraintViolationFactory constraintViolationFactory,
                                  final ClientInfoProvider clientInfoProvider,
                                  final NodeAccess nodeAccess,
                                  final AssetBlobValidators assetBlobValidators)
  {
    this.blobStoreManager = checkNotNull(blobStoreManager);
    this.dataSessionSupplier = checkNotNull(dataSessionSupplier);
    this.constraintViolationFactory = checkNotNull(constraintViolationFactory);
    this.clientInfoProvider = checkNotNull(clientInfoProvider);
    this.nodeAccess = checkNotNull(nodeAccess);
    this.assetBlobValidators = checkNotNull(assetBlobValidators);
  }
}
