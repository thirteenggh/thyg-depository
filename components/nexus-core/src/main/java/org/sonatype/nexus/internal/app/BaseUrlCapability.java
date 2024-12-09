package org.sonatype.nexus.internal.app;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.sonatype.nexus.capability.CapabilitySupport;
import org.sonatype.nexus.common.app.BaseUrlManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Base-URL capability.
 *
 * @since 3.0
 */
@Named(BaseUrlCapabilityDescriptor.TYPE_ID)
public class BaseUrlCapability
    extends CapabilitySupport<BaseUrlCapabilityConfiguration>
{
  private final BaseUrlManager baseUrlManager;

  @Inject
  public BaseUrlCapability(final BaseUrlManager baseUrlManager) {
    this.baseUrlManager = checkNotNull(baseUrlManager);
  }

  @Override
  protected BaseUrlCapabilityConfiguration createConfig(final Map<String, String> properties) {
    return new BaseUrlCapabilityConfiguration(properties);
  }

  @Override
  protected void onActivate(final BaseUrlCapabilityConfiguration config) throws Exception {
    baseUrlManager.setUrl(config.getUrl());
  }

  @Override
  protected void onPassivate(final BaseUrlCapabilityConfiguration config) throws Exception {
    baseUrlManager.setUrl(null);
  }
}
