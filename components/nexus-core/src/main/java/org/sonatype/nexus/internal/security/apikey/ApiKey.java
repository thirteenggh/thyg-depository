package org.sonatype.nexus.internal.security.apikey;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * A database-stored object representing the association between a {@link PrincipalCollection} and a Api Key (char[]).
 *
 * @since 3.20
 */
public interface ApiKey
{
  String getDomain();

  void setDomain(final String domain);

  PrincipalCollection getPrincipals();

  void setPrincipals(final PrincipalCollection principals);

  char[] getApiKey();

  void setApiKey(final char[] apiKey);
}
