package org.sonatype.nexus.internal.security.apikey;

import java.util.Optional;

import org.sonatype.nexus.datastore.api.DataAccess;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * {@link ApiKeyData} access.
 *
 * @since 3.21
 */
public interface ApiKeyDAO
    extends DataAccess
{
  Iterable<PrincipalCollection> browsePrincipals();

  Optional<ApiKeyToken> findApiKey(@Param("domain") String domain, @Param("primaryPrincipal") String primaryPrincipal);

  Optional<PrincipalCollection> findPrincipals(@Param("domain") String domain, @Param("token") ApiKeyToken token);

  void save(ApiKeyData apiKeyData);

  boolean deleteDomainKey(@Param("domain") String domain, @Param("primaryPrincipal") String primaryPrincipal);

  boolean deleteKeys(@Param("primaryPrincipal") String primaryPrincipal);

  boolean deleteAllKeys();
}
