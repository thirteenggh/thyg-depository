package org.sonatype.nexus.security.authc.apikey;

import org.apache.shiro.subject.PrincipalCollection;

/**
 * API Key Factory that creates API Keys. If some specific content or format needed, implement one as SISU component and
 * use same name as your {@link ApiKeyExtractor} component has.
 *
 * @since 3.0
 */
public interface ApiKeyFactory
{
  /**
   * Creates a domain specific API Key, never {@code null}.
   */
  char[] makeApiKey(final PrincipalCollection principals);
}
