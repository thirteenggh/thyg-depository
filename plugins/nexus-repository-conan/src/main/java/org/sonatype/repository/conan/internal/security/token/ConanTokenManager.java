package org.sonatype.repository.conan.internal.security.token;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.SecurityHelper;
import org.sonatype.nexus.security.authc.apikey.ApiKeyStore;
import org.sonatype.nexus.security.token.BearerTokenManager;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import static org.sonatype.nexus.security.anonymous.AnonymousHelper.isAnonymous;

/**
 * @since 3.28
 */
@Named
@Singleton
public class ConanTokenManager
    extends BearerTokenManager
{
  @Inject
  public ConanTokenManager(final ApiKeyStore apiKeyStore,
                           final SecurityHelper securityHelper)
  {
    super(apiKeyStore, securityHelper, ConanToken.NAME);
  }

  /**
   * Verifies passed in principal/credentials combo, and creates (if not already exists) a npm token mapped to given
   * principal and returns the newly created token.
   */
  public String login() {
    Subject subject = securityHelper.subject();
    boolean authenticated = subject.getPrincipal() != null && subject.isAuthenticated();
    if (authenticated || isAnonymous(subject)) {
      PrincipalCollection principals = subject.getPrincipals();
      return super.createToken(principals);
    }
    return null;
  }

  public String user() {
    Subject subject = securityHelper.subject();
    boolean authenticated = subject.getPrincipal() != null && subject.isAuthenticated();
    if (authenticated) {
      return subject.getPrincipals().getPrimaryPrincipal().toString();
    }
    return null;
  }
}
