package org.sonatype.nexus.onboarding;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * @since 3.17
 */
@Singleton
public class OnboardingConfiguration
{
  private boolean enabled;

  @Inject
  public OnboardingConfiguration(@Named("${nexus.onboarding.enabled:-true}") final boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
