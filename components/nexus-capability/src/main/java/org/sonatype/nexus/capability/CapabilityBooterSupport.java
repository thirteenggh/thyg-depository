package org.sonatype.nexus.capability;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.capability.CapabilityRegistryEvent.Ready;
import org.sonatype.nexus.common.event.EventAware;

import com.google.common.base.Throwables;
import com.google.common.eventbus.Subscribe;

/**
 * Support for components which need to handle capability registration upon booting.
 *
 * @since capabilities 2.2
 */
public abstract class CapabilityBooterSupport
  extends ComponentSupport
  implements EventAware
{
  @Subscribe
  public void handle(final Ready event) {
    final CapabilityRegistry registry = event.getCapabilityRegistry();
    try {
      boot(registry);
    }
    catch (Exception e) {
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
  }

  protected abstract void boot(final CapabilityRegistry registry) throws Exception;

  protected void maybeAddCapability(final CapabilityRegistry capabilityRegistry,
                                    final CapabilityType type,
                                    final boolean enabled,
                                    @Nullable final String notes,
                                    @Nullable final Map<String, String> properties)
      throws Exception
  {
    CapabilityReference reference = findCapability(capabilityRegistry, type);
    if (reference == null) {
      log.debug("Automatically adding capability type: {}; enabled: {}", type, enabled);
      addCapability(capabilityRegistry, type, enabled, notes, properties);
    }
  }

  protected CapabilityReference findCapability(final CapabilityRegistry capabilityRegistry,
                                               final CapabilityType type)
  {
    return findCapability(capabilityRegistry, type, true);
  }

  protected CapabilityReference findCapability(final CapabilityRegistry capabilityRegistry,
                                               final CapabilityType type,
                                               final boolean includeNotExposed)
  {
    CapabilityReferenceFilterBuilder.CapabilityReferenceFilter filter = CapabilityReferenceFilterBuilder.capabilities().withType(type);

    if (includeNotExposed) {
      filter.includeNotExposed();
    }

    Collection<? extends CapabilityReference> capabilities = capabilityRegistry.get(filter);
    if (capabilities != null && !capabilities.isEmpty()) {
      return capabilities.iterator().next();
    }
    return null;
  }

  protected void addCapability(final CapabilityRegistry capabilityRegistry,
                               final CapabilityType type,
                               final boolean enabled,
                               @Nullable final String notes,
                               @Nullable final Map<String, String> properties)
      throws Exception
  {
    capabilityRegistry.add(type, enabled, notes == null ? "Automatically added on " + new Date() : notes, properties);
  }
}
