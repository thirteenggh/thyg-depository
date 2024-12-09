package org.sonatype.nexus.rapture.internal.settings;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.rapture.UiSettingsManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Rapture Settings capability.
 *
 * @since 3.0
 */
@Named(SettingsCapabilityDescriptor.TYPE_ID)
public class SettingsCapability
    extends CapabilitySupport<SettingsCapabilityConfiguration>
{

  private final UiSettingsManager rapture;

  @Inject
  public SettingsCapability(final UiSettingsManager rapture) {
    this.rapture = checkNotNull(rapture);
  }

  @Override
  protected SettingsCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new SettingsCapabilityConfiguration(properties);
  }

  @Override
  protected void onActivate(final SettingsCapabilityConfiguration config) throws Exception {
    rapture.setSettings(config);
  }

  @Override
  protected void onPassivate(final SettingsCapabilityConfiguration config) throws Exception {
    rapture.resetSettings();
  }

  @Override
  protected void onRemove(final SettingsCapabilityConfiguration config) throws Exception {
    rapture.resetSettings();
  }

  @Override
  public Condition activationCondition() {
    return conditions().capabilities().passivateCapabilityDuringUpdate();
  }

}
