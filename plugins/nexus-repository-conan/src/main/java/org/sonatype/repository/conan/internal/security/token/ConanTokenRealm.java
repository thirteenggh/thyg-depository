package org.sonatype.repository.conan.internal.security.token;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.UserPrincipalsHelper;
import org.sonatype.nexus.security.authc.apikey.ApiKeyStore;
import org.sonatype.nexus.security.token.BearerTokenRealm;

import org.eclipse.sisu.Description;

/**
 * @since 3.28
 */
@Named
@Singleton
@Description("Conan Bearer Token Realm")
public class ConanTokenRealm
    extends BearerTokenRealm
{
  @Inject
  public ConanTokenRealm(final ApiKeyStore keyStore, final UserPrincipalsHelper principalsHelper) {
    super(keyStore, principalsHelper, ConanToken.NAME);
  }

  @Override
  protected boolean isAnonymousSupported() {
    return false;
  }
}
