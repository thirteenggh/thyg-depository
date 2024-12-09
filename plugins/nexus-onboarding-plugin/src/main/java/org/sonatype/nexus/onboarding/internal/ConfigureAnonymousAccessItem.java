package org.sonatype.nexus.onboarding.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.onboarding.OnboardingItem;
import org.sonatype.nexus.security.anonymous.AnonymousManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.17
 */
@Named
@Singleton
public class ConfigureAnonymousAccessItem
  extends ComponentSupport
  implements OnboardingItem
{
  private final AnonymousManager anonymousManager;

  @Inject
  public ConfigureAnonymousAccessItem(final AnonymousManager anonymousManager) {
    this.anonymousManager = checkNotNull(anonymousManager);
  }

  @Override
  public String getType() {
    return "ConfigureAnonymousAccess";
  }

  @Override
  public int getPriority() {
    return 32;
  }

  @Override
  public boolean applies() {
    return !anonymousManager.isConfigured();
  }
}
