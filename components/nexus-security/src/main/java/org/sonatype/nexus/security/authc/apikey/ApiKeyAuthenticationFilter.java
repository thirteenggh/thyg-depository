package org.sonatype.nexus.security.authc.apikey;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.sonatype.nexus.common.text.Strings2;
import org.sonatype.nexus.security.authc.NexusApiKeyAuthenticationToken;
import org.sonatype.nexus.security.authc.NexusBasicHttpAuthenticationFilter;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import static com.google.common.base.Preconditions.checkNotNull;

// FIXME: This isn't basic-auth, so shouldn't extend from that base-class?

/**
 * {@link AuthenticatingFilter} that looks for credentials with help of registered {@link ApiKeyExtractor}s.
 */
public class ApiKeyAuthenticationFilter
    extends NexusBasicHttpAuthenticationFilter
{
  private static final String NX_APIKEY_PRINCIPAL = ApiKeyAuthenticationFilter.class.getName() + ".principal";

  private static final String NX_APIKEY_TOKEN = ApiKeyAuthenticationFilter.class.getName() + ".apiKey";

  public static final String NAME = "nx-apikey-authc";

  private final Map<String, ApiKeyExtractor> apiKeys;

  @Inject
  public ApiKeyAuthenticationFilter(final Map<String, ApiKeyExtractor> apiKeys) {
    this.apiKeys = checkNotNull(apiKeys);
  }

  @Override
  protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
    final HttpServletRequest http = WebUtils.toHttp(request);
    for (final Map.Entry<String, ApiKeyExtractor> apiKeyEntry : apiKeys.entrySet()) {
      final String apiKey = apiKeyEntry.getValue().extract(http);
      if (null != apiKey) {
        log.trace("ApiKeyExtractor {} detected presence of API Key", apiKeyEntry.getKey());
        request.setAttribute(NX_APIKEY_PRINCIPAL, apiKeyEntry.getKey());
        request.setAttribute(NX_APIKEY_TOKEN, apiKey);
        return true;
      }
    }
    return super.isLoginAttempt(request, response);
  }

  @Override
  protected AuthenticationToken createToken(final ServletRequest request, final ServletResponse response) {
    final String principal = (String) request.getAttribute(NX_APIKEY_PRINCIPAL);
    final String token = (String) request.getAttribute(NX_APIKEY_TOKEN);
    if (!Strings2.isBlank(principal) && !Strings2.isBlank(token)) {
      return new NexusApiKeyAuthenticationToken(principal, token.toCharArray(), request.getRemoteHost());
    }
    return super.createToken(request, response);
  }
}
