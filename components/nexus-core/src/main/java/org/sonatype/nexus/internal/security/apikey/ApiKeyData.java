package org.sonatype.nexus.internal.security.apikey;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * {@link ApiKey} data.
 *
 * @since 3.21
 */
public class ApiKeyData
    implements ApiKey
{
  private String domain;

  private PrincipalCollection principals;

  private ApiKeyToken token;

  @Override
  public void setDomain(final String domain) {
    this.domain = domain;
  }

  @Override
  public void setPrincipals(final PrincipalCollection principals) {
    this.principals = principals;
  }

  public void setToken(final ApiKeyToken token) {
    this.token = token;
  }

  @Override
  public void setApiKey(final char[] chars) {
    this.token = new ApiKeyToken(chars);
  }

  @Override
  public String getDomain() {
    return domain;
  }

  public String getPrimaryPrincipal() {
    return String.valueOf(principals.getPrimaryPrincipal());
  }

  @Override
  public PrincipalCollection getPrincipals() {
    return principals;
  }

  public ApiKeyToken getToken() {
    return token;
  }

  @Override
  public char[] getApiKey() {
    return token.getChars();
  }
}
