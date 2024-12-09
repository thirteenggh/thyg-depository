package org.sonatype.nexus.repository.content.npm.internal;

import java.io.IOException;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.AssetBlob;
import org.sonatype.nexus.repository.npm.internal.NpmAuditTarballFacet;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.vulnerability.exceptions.TarballLoadingException;

import static java.util.Optional.ofNullable;
import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;

/**
 * Data store {@link NpmAuditTarballFacet}
 *
 * @since 3.29
 */
@Named
public class DataStoreNpmAuditTarballFacet
    extends NpmAuditTarballFacet
{
  @Inject
  public DataStoreNpmAuditTarballFacet(
      @Named("${nexus.npm.audit.maxConcurrentRequests:-10}") final int maxConcurrentRequests)
  {
    super(maxConcurrentRequests);
  }

  @Override
  protected Optional<String> getComponentHashsumForProxyRepo(final Repository repository, final Context context)
      throws TarballLoadingException
  {
    try {
      return getComponentHashsum(repository, context);
    }
    catch (IOException e) {
      throw new TarballLoadingException(e.getMessage(), e);
    }
  }

  @Override
  protected Optional<String> getHashsum(final AttributesMap attributes) {
    return ofNullable(attributes.get(Asset.class))
        .map(Asset::blob)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(AssetBlob::checksums)
        .map(checksums -> checksums.get(SHA1.name()));
  }
}
