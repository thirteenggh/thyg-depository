package org.sonatype.nexus.security.token;

import javax.inject.Inject;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.security.SecurityHelper;
import org.sonatype.nexus.security.authc.apikey.ApiKeyStore;

import org.apache.shiro.subject.PrincipalCollection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Allows the logic for managing bearer tokens to be shared between formats and it is intended that this will be
 * subclassed and a format-specific concrete implementation provided.
 *
 * @since 3.6
 */
public abstract class BearerTokenManager
    extends ComponentSupport
{
  protected final ApiKeyStore apiKeyStore;

  protected final SecurityHelper securityHelper;

  private final String format;

  @Inject
  public BearerTokenManager(final ApiKeyStore apiKeyStore,
                            final SecurityHelper securityHelper,
                            final String format)
  {
    this.apiKeyStore = checkNotNull(apiKeyStore);
    this.securityHelper = checkNotNull(securityHelper);
    this.format = checkNotNull(format);
  }

  /**
   * Creates (if not already exists) a bearer token mapped to given principal and returns the newly created token.
   */
  protected String createToken(final PrincipalCollection principals) {
    checkNotNull(principals);
    char[] apiKey = apiKeyStore.getApiKey(format, principals);
    if (apiKey != null) {
      return format + "." + new String(apiKey);
    }
    return format + "." + new String(apiKeyStore.createApiKey(format, principals));
  }

  /**
   * Removes any Bearer token for current user, if exists, and returns {@code true}.
   */
  public boolean deleteToken() {
    final PrincipalCollection principals = securityHelper.subject().getPrincipals();
    if (apiKeyStore.getApiKey(format, principals) != null) {
      apiKeyStore.deleteApiKey(format, securityHelper.subject().getPrincipals());
      return true;
    }
    return false;
  }
}
