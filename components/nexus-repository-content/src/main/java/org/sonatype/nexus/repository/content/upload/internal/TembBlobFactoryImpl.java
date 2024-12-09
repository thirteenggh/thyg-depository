package org.sonatype.nexus.repository.content.upload.internal;

import java.io.InputStream;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.facet.ContentFacet;
import org.sonatype.nexus.repository.upload.TempBlobFactory;
import org.sonatype.nexus.repository.view.Payload;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * Content implementation of {@code TempBlobFactory}
 *
 * @since 3.24
 */
@Named
@Singleton
public class TembBlobFactoryImpl
    extends ComponentSupport
    implements TempBlobFactory
{
  @Override
  public TempBlob create(final Repository repository,
                         final InputStream inputStream,
                         final Iterable<HashAlgorithm> hashAlgorithms)
  {
    return repository.facet(ContentFacet.class).blobs().ingest(inputStream, null, hashAlgorithms);
  }

  @Override
  public TempBlob create(final Repository repository,
                         final Payload payload,
                         final Iterable<HashAlgorithm> hashAlgorithms)
  {
    return repository.facet(ContentFacet.class).blobs().ingest(payload, hashAlgorithms);
  }
}
