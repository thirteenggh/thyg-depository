package org.sonatype.nexus.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.security.FilterProviderSupport;
import org.sonatype.nexus.security.anonymous.AnonymousFilter;
import org.sonatype.nexus.security.authc.AntiCsrfFilter;
import org.sonatype.nexus.security.authc.NexusAuthenticationFilter;
import org.sonatype.nexus.security.authc.apikey.ApiKeyAuthenticationFilter;
import org.sonatype.nexus.security.authz.PermissionsFilter;

import com.google.inject.AbstractModule;

import static org.sonatype.nexus.security.FilterProviderSupport.filterKey;

/**
 * Security module.
 */
@Named
public class SecurityModule
    extends AbstractModule
{
  @Override
  protected void configure() {
    bind(filterKey(AnonymousFilter.NAME)).to(AnonymousFilter.class);
    bind(filterKey(NexusAuthenticationFilter.NAME)).to(NexusAuthenticationFilter.class);
    bind(filterKey(ApiKeyAuthenticationFilter.NAME)).to(ApiKeyAuthenticationFilter.class);
    bind(filterKey(PermissionsFilter.NAME)).to(PermissionsFilter.class);
    bind(filterKey(AntiCsrfFilter.NAME)).to(AntiCsrfFilter.class);

    // FIXME: Sort out, and deal with naming the "authcBasic" are presently auth-token bits
    bind(filterKey("authcBasic")).toProvider(AuthcBasicFilterProvider.class);
    bind(filterKey("authcAntiCsrf")).toProvider(AuthcAntiCsrfFilterProvider.class);

    // FIXME: This likely should be normalized with the auth-token bits
    bind(filterKey("authcApiKey")).toProvider(AuthcApiKeyFilterProvider.class);
  }

  // FIXME: Probably do not need provider here at all anymore

  @Singleton
  static class AuthcBasicFilterProvider
      extends FilterProviderSupport
  {
    @Inject
    AuthcBasicFilterProvider(final NexusAuthenticationFilter filter) {
      super(filter);
    }
  }

  @Singleton
  static class AuthcApiKeyFilterProvider
      extends FilterProviderSupport
  {
    @Inject
    AuthcApiKeyFilterProvider(final ApiKeyAuthenticationFilter filter) {
      super(filter);
    }
  }

  @Singleton
  static class AuthcAntiCsrfFilterProvider
      extends FilterProviderSupport
  {
    @Inject
    AuthcAntiCsrfFilterProvider(final AntiCsrfFilter filter) {
      super(filter);
    }
  }
}
