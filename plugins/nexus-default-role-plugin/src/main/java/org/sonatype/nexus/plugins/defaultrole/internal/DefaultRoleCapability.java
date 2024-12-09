package org.sonatype.nexus.plugins.defaultrole.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.goodies.i18n.I18N;
import org.sonatype.goodies.i18n.MessageBundle;
import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.plugins.defaultrole.DefaultRoleRealm;
import org.sonatype.nexus.security.realm.RealmManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Capability that allows selecting a role to apply to all authorized users
 *
 * @since 3.22
 */
@Named(DefaultRoleCapabilityDescriptor.TYPE_ID)
public class DefaultRoleCapability
    extends CapabilitySupport<DefaultRoleCapabilityConfiguration>
{
  private interface Messages
      extends MessageBundle
  {
    @DefaultMessage("%s")
    String description(String role);
  }

  private static final Messages messages = I18N.create(Messages.class);

  private final RealmManager realmManager;

  private final DefaultRoleRealm defaultRoleRealm;

  @Inject
  public DefaultRoleCapability(final RealmManager realmManager, final DefaultRoleRealm defaultRoleRealm) {
    this.realmManager = checkNotNull(realmManager);
    this.defaultRoleRealm = checkNotNull(defaultRoleRealm);
  }

  @Override
  protected DefaultRoleCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new DefaultRoleCapabilityConfiguration(properties);
  }

  @Override
  protected String renderDescription() {
    return messages.description(defaultRoleRealm.getRole());
  }

  @Override
  public Condition activationCondition() {
    return conditions().capabilities().passivateCapabilityDuringUpdate();
  }

  @Override
  protected void onActivate(final DefaultRoleCapabilityConfiguration defaultRoleCapabilityConfiguration) {
    defaultRoleRealm.setRole(defaultRoleCapabilityConfiguration.getRole());

    // install realm if needed
    realmManager.enableRealm(DefaultRoleRealm.NAME);
  }

  @Override
  protected void onPassivate(final DefaultRoleCapabilityConfiguration defaultRoleCapabilityConfiguration) {
    realmManager.disableRealm(DefaultRoleRealm.NAME);
    defaultRoleRealm.setRole(null);
  }
}
