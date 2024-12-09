package org.sonatype.nexus.repository.npm.internal.orient;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.common.hash.HashAlgorithm;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.npm.internal.NpmAuditTarballFacet;
import org.sonatype.nexus.repository.storage.StorageFacet;
import org.sonatype.nexus.repository.view.Context;
import org.sonatype.nexus.repository.vulnerability.exceptions.TarballLoadingException;
import org.sonatype.nexus.transaction.UnitOfWork;

import com.google.common.hash.HashCode;

import static org.sonatype.nexus.common.hash.HashAlgorithm.SHA1;
import static org.sonatype.nexus.repository.view.Content.CONTENT_HASH_CODES_MAP;
import static org.sonatype.nexus.repository.view.Content.T_CONTENT_HASH_CODES_MAP;

/**
 * Orient {@link NpmAuditTarballFacet}
 *
 * @since 3.29
 */
@Named
@Priority(Integer.MAX_VALUE)
public class OrientNpmAuditTarballFacet
    extends NpmAuditTarballFacet
{
  @Inject
  public OrientNpmAuditTarballFacet(
      @Named("${nexus.npm.audit.maxConcurrentRequests:-10}") final int maxConcurrentRequests)
  {
    super(maxConcurrentRequests);
  }

  @Override
  protected Optional<String> getComponentHashsumForProxyRepo(final Repository repository, final Context context)
      throws TarballLoadingException
  {
    try {
      UnitOfWork.begin(repository.facet(StorageFacet.class).txSupplier());
      return getComponentHashsum(repository, context);
    }
    catch (IOException e) {
      throw new TarballLoadingException(e.getMessage(), e);
    }
    finally {
      UnitOfWork.end();
    }
  }

  @Override
  protected Optional<String> getHashsum(final AttributesMap attributes) {
    Map<HashAlgorithm, HashCode> hashMap = attributes.get(CONTENT_HASH_CODES_MAP, T_CONTENT_HASH_CODES_MAP);
    if (hashMap != null) {
      HashCode hashCode = hashMap.get(SHA1);
      return Optional.ofNullable(hashCode.toString());
    }
    return Optional.empty();
  }
}
