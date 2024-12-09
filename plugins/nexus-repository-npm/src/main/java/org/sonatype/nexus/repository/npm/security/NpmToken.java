package org.sonatype.nexus.repository.npm.security;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.token.BearerToken;

/**
 * npm API-Key; used by npm CLI to authenticate.
 */
@Named(NpmToken.NAME)
@Singleton
public final class NpmToken
    extends BearerToken
{
  public static final String NAME = "NpmToken";

  public NpmToken() {
    super(NAME);
  }

  @Override
  protected boolean matchesFormat(final List<String> parts) {
    return super.matchesFormat(parts) || !parts.get(1).contains(".");
  }
}
