package org.sonatype.nexus.security.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.nexus.security.anonymous.AnonymousConfiguration;

/**
 * Initial {@link AnonymousConfiguration} provider.
 *
 * Provides the initial configuration of anonymous configuration for fresh server installations.
 *
 * @since 3.0
 */
@Named("initial")
@Singleton
public class InitialAnonymousConfigurationProvider
  implements Provider<AnonymousConfiguration>
{
  private final boolean enabled;

  @Inject
  public InitialAnonymousConfigurationProvider(
      @Named("${nexus.security.default.anonymous:-true}") final boolean enabled)
  {
    this.enabled = enabled;
  }

  @Override
  public AnonymousConfiguration get() {
    return new InitialAnonymousConfiguration();
  }

  private class InitialAnonymousConfiguration
      implements AnonymousConfiguration
  {
    @Override
    public AnonymousConfiguration copy() {
      return this;
    }

    @Override
    public String getRealmName() {
      return AnonymousConfiguration.DEFAULT_REALM_NAME;
    }

    @Override
    public String getUserId() {
      return AnonymousConfiguration.DEFAULT_USER_ID;
    }

    @Override
    public boolean isEnabled() {
      return enabled;
    }

    @Override
    public void setEnabled(final boolean enabled) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setRealmName(final String realmName) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void setUserId(final String userId) {
      throw new UnsupportedOperationException();
    }
  }
}
