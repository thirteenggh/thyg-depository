package org.sonatype.nexus.repository.npm.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.npm.security.NpmToken;

import org.sonatype.nexus.security.UserPrincipalsHelper;
import org.sonatype.nexus.security.authc.apikey.ApiKeyStore;
import org.sonatype.nexus.security.token.BearerTokenRealm;

import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.subject.Subject;
import org.eclipse.sisu.Description;

/**
 * {@link AuthenticatingRealm} that maps npm tokens to valid {@link Subject}s.
 *
 * @since 3.0
 */
@Named(NpmToken.NAME)
@Singleton
@Description("npm Bearer Token Realm")
public final class NpmTokenRealm
    extends BearerTokenRealm
{
  @Inject
  public NpmTokenRealm(final ApiKeyStore keyStore, final UserPrincipalsHelper principalsHelper) {
    super(keyStore, principalsHelper, NpmToken.NAME);
  }
}
