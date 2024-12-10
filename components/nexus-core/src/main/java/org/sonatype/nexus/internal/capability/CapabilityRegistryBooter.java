package org.sonatype.nexus.internal.capability;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityRegistryEvent.Ready;
import org.sonatype.nexus.common.app.ManagedLifecycle;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;

import com.google.inject.Provider;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.sonatype.nexus.common.app.ManagedLifecycle.Phase.CAPABILITIES;

@Named
@ManagedLifecycle(phase = CAPABILITIES)
@Singleton
public class CapabilityRegistryBooter
    extends StateGuardLifecycleSupport
{
  private final EventManager eventManager;

  private final Provider<DefaultCapabilityRegistry> capabilityRegistryProvider;

  @Inject
  public CapabilityRegistryBooter(final EventManager eventManager,
                                  final Provider<DefaultCapabilityRegistry> capabilityRegistryProvider)
  {
    this.eventManager = checkNotNull(eventManager);
    this.capabilityRegistryProvider = checkNotNull(capabilityRegistryProvider);
  }

  @Override
  protected void doStart() throws Exception {
    DefaultCapabilityRegistry registry = capabilityRegistryProvider.get();
    registry.load();

    // fire event when the registry is loaded and ready for use
    eventManager.post(new Ready(registry));
  }
}
