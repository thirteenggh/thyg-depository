package org.sonatype.nexus.repository.upload.cma;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.upload.TempBlobFactory;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * CMA implementation of {@link TempBlobFactory}
 *
 * @since 3.24
 */
@Named("default")
@Singleton
public class TempBlobFactoryImpl
    extends ComponentSupport
    implements TempBlobFactory
{
  @Override
  public TempBlob create(final Repository repository,
                         final InputStream inputStream,
                         final Iterable<HashAlgorithm> hashAlgorithms)
  {
    return repository.facet(StorageFacet.class).createTempBlob(inputStream, hashAlgorithms);
  }

  @Override
  public TempBlob create(final Repository repository,
                         final Payload payload,
                         final Iterable<HashAlgorithm> hashAlgorithms)
  {
    return repository.facet(StorageFacet.class).createTempBlob(payload, hashAlgorithms);
  }
}
