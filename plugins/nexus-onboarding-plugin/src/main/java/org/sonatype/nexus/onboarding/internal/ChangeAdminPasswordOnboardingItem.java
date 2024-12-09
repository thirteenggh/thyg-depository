package org.sonatype.nexus.onboarding.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.onboarding.OnboardingItem;
import org.sonatype.nexus.security.SecuritySystem;
import org.sonatype.nexus.security.internal.UserManagerImpl;
import org.sonatype.nexus.security.user.NoSuchUserManagerException;
import org.sonatype.nexus.security.user.User;
import org.sonatype.nexus.security.user.UserNotFoundException;
import org.sonatype.nexus.security.user.UserStatus;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @since 3.17
 */
@Named
@Singleton
public class ChangeAdminPasswordOnboardingItem
  extends ComponentSupport
  implements OnboardingItem
{
  private final SecuritySystem securitySystem;

  @Inject
  public ChangeAdminPasswordOnboardingItem(final SecuritySystem securitySystem) {
    this.securitySystem = checkNotNull(securitySystem);
  }

  @Override
  public String getType() {
    return "ChangeAdminPassword";
  }

  @Override
  public int getPriority() {
    return 0;
  }

  @Override
  public boolean applies() {
    try {
      User user = securitySystem.getUser("admin", UserManagerImpl.DEFAULT_SOURCE);
      return UserStatus.changepassword.equals(user.getStatus());
    }
    catch (UserNotFoundException e) {
      log.trace("admin user not found in system, marking onboarding item as not applicable.", e);
    }
    catch (NoSuchUserManagerException e) {
      log.trace("default UserManager not found in system, marking onboarding item as not applicable.", e);
    }

    return false;
  }
}
