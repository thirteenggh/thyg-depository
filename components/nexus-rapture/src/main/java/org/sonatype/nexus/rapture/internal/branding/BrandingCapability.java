package org.sonatype.nexus.rapture.internal.branding;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.capability.Condition;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Branding capability.
 *
 * @since 3.0
 */
@Named(BrandingCapabilityDescriptor.TYPE_ID)
public class BrandingCapability
    extends CapabilitySupport<BrandingCapabilityConfiguration>
{

  private final Branding branding;

  @Inject
  public BrandingCapability(final Branding branding) {
    this.branding = checkNotNull(branding);
  }

  @Override
  protected BrandingCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new BrandingCapabilityConfiguration(properties);
  }

  @Override
  protected void onActivate(final BrandingCapabilityConfiguration config) throws Exception {
    branding.set(config);
  }

  @Override
  protected void onPassivate(final BrandingCapabilityConfiguration config) throws Exception {
    branding.reset();
  }

  @Override
  protected void onRemove(final BrandingCapabilityConfiguration config) throws Exception {
    branding.reset();
  }

  @Override
  public Condition activationCondition() {
    return conditions().capabilities().passivateCapabilityDuringUpdate();
  }

}
