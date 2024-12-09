package org.sonatype.repository.conan.internal.security.token;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.token.BearerToken;

/**
 * @since 3.28
 */
@Named(ConanToken.NAME)
@Singleton
public class ConanToken
    extends BearerToken
{
  public final static String NAME = "ConanToken";

  public ConanToken() {
    super(NAME);
  }
}
