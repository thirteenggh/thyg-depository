package org.sonatype.nexus.repository.httpbridge.legacy;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityReference;
import org.sonatype.nexus.capability.CapabilityRegistry;
import org.sonatype.nexus.common.property.SystemPropertiesHelper;
import org.sonatype.nexus.repository.httpbridge.internal.HttpBridgeModule;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.capability.CapabilityReferenceFilterBuilder.capabilities;

@Named
@Singleton
public class LegacyUrlEnabledHelper
{
  private final boolean supportLegacyContent = SystemPropertiesHelper
      .getBoolean(HttpBridgeModule.class.getName() + ".legacy", false);

  private final CapabilityRegistry capabilities;

  @Inject
  public LegacyUrlEnabledHelper(final CapabilityRegistry capabilities)
  {
    this.capabilities = checkNotNull(capabilities);
  }

  public boolean isEnabled() {
    return supportLegacyContent || isLegacyUrlCapabilityActive();
  }

  private boolean isLegacyUrlCapabilityActive() {
    Collection<? extends CapabilityReference> references = capabilities
        .get(capabilities().withType(LegacyUrlCapabilityDescriptor.TYPE));

    if (references.isEmpty()) {
      return false;
    }

    return references.iterator().next().context().isActive();
  }
}
