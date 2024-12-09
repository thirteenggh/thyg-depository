package org.sonatype.nexus.repository.storage;

import java.io.InputStream;
import java.util.function.Supplier;

import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.config.WritePolicy;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;
import org.sonatype.nexus.transaction.UnitOfWork;

/**
 * Storage {@link Facet}, providing component and asset storage for a repository.
 *
 * @since 3.0
 */
@Facet.Exposed
public interface StorageFacet
    extends Facet
{

  /**
   * Registers format specific selector for {@link WritePolicy}. If not set, the {@link
   * WritePolicySelector#DEFAULT} is used which returns the configured write policy.
   */
  void registerWritePolicySelector(WritePolicySelector writePolicySelector);

  /**
   * Supplies transactions for use in {@link UnitOfWork}.
   */
  Supplier<StorageTx> txSupplier();

  /**
   * Creates a new temporary blob based using the contents of the input stream. Disposal of the temp blob must be
   * managed by the caller, typically using a try-with-resources block.
   *
   * @since 3.1
   */
  TempBlob createTempBlob(InputStream inputStream, Iterable<HashAlgorithm> hashAlgorithms);

  /**
   * Creates a new temporary blob based using the contents of the payload. Disposal of the temp blob must be
   * managed by the caller, typically using a try-with-resources block.
   *
   * @since 3.1
   */
  TempBlob createTempBlob(Payload payload, Iterable<HashAlgorithm> hashAlgorithms);

  /**
   * Returns the {@link BlobStore} associated with the repository.
   *
   * @since 3.10
   */
  BlobStore blobStore();
}
