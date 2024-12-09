package org.sonatype.nexus.security.realm;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link RealmExists} validator.
 *
 * @since 3.0
 */
@Named
public class RealmExistsValidator
    extends ConstraintValidatorSupport<RealmExists, String>
{
  private final RealmSecurityManager realmSecurityManager;

  @Inject
  public RealmExistsValidator(final RealmSecurityManager realmSecurityManager) {
    this.realmSecurityManager = checkNotNull(realmSecurityManager);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    log.trace("Validating realm exists: {}", value);
    for (Realm realm : realmSecurityManager.getRealms()) {
      if (value.equals(realm.getName())) {
        return true;
      }
    }
    return false;
  }
}
