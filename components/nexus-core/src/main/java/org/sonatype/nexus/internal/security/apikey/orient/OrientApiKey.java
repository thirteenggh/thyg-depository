package org.sonatype.nexus.internal.security.apikey.orient;

import java.util.Arrays;

import org.sonatype.nexus.common.entity.AbstractEntity;
import org.sonatype.nexus.internal.security.apikey.ApiKey;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * An Orient-stored object representing the association between a {@link PrincipalCollection} and a Api Key (char[]).
 *
 * @since 3.0
 */
public class OrientApiKey
    extends AbstractEntity
    implements ApiKey
{
  private String domain;

  private PrincipalCollection principals;

  private char[] apiKey;

  OrientApiKey() {
    // package-private constructor
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public void setPrincipals(final PrincipalCollection principals) {
    this.principals = principals;
  }

  public void setApiKey(final char[] apiKey) {
    this.apiKey = Arrays.copyOf(apiKey, apiKey.length);
  }

  public String getDomain() {
    return domain;
  }

  public PrincipalCollection getPrincipals() {
    return principals;
  }

  public char[] getApiKey() {
    return Arrays.copyOf(apiKey, apiKey.length);
  }
}
