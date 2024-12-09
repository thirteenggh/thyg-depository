package org.sonatype.nexus.security.authc.apikey;

import javax.annotation.Nullable;

import org.sonatype.goodies.lifecycle.Lifecycle;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * Persistent mapping between principals (such as user IDs) and API-Keys.
 *
 * @since 3.0
 */
public interface ApiKeyStore
    extends Lifecycle
{
  /**
   * Creates an API-Key and assigns it to the given principals in given domain.
   */
  char[] createApiKey(String domain, PrincipalCollection principals);

  /**
   * Persists an API-Key with a predetermined value.
   *
   * @since 3.1
   */
  void persistApiKey(String domain, PrincipalCollection principals, char[] apiKey);

  /**
   * Gets the current API-Key assigned to the given principals in given domain.
   *
   * @return {@code null} if no key has been assigned
   */
  @Nullable
  char[] getApiKey(String domain, PrincipalCollection principals);

  /**
   * Retrieves the principals associated with the given API-Key in given domain.
   *
   * @return {@code null} if the key is invalid or stale
   */
  @Nullable
  PrincipalCollection getPrincipals(String domain, char[] apiKey);

  /**
   * Deletes the API-Key associated with the given principals in given domain.
   */
  void deleteApiKey(String domain, PrincipalCollection principals);

  /**
   * Deletes every API-Key associated with the given principals in every domain.
   */
  void deleteApiKeys(PrincipalCollection principals);

  /**
   * Deletes all API-Keys.
   * 
   * @since 3.1
   */
  void deleteApiKeys();

  /**
   * Purges any API-Keys associated with missing/deleted users.
   */
  void purgeApiKeys();
}
