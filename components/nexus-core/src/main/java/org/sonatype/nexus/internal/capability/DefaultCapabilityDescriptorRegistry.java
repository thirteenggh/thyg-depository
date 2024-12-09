package org.sonatype.nexus.internal.capability;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.CapabilityDescriptor;
import org.sonatype.nexus.capability.CapabilityDescriptorRegistry;
import org.sonatype.nexus.capability.CapabilityType;

import com.google.common.collect.Lists;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Singleton
class DefaultCapabilityDescriptorRegistry
    implements CapabilityDescriptorRegistry
{

  private final List<CapabilityDescriptor> descriptors;

  private final Set<CapabilityDescriptor> dynamicDescriptors;

  @Inject
  DefaultCapabilityDescriptorRegistry(final List<CapabilityDescriptor> descriptors) {
    this.descriptors = checkNotNull(descriptors);
    this.dynamicDescriptors = new CopyOnWriteArraySet<CapabilityDescriptor>();
  }

  @Override
  public CapabilityDescriptorRegistry register(final CapabilityDescriptor capabilityDescriptor) {
    dynamicDescriptors.add(checkNotNull(capabilityDescriptor));
    return this;
  }

  @Override
  public CapabilityDescriptorRegistry unregister(final CapabilityDescriptor capabilityDescriptor) {
    dynamicDescriptors.remove(checkNotNull(capabilityDescriptor));
    return this;
  }

  @Override
  public CapabilityDescriptor get(final CapabilityType capabilityType) {
    final CapabilityDescriptor descriptor = get(descriptors, capabilityType);
    if (descriptor == null) {
      return get(dynamicDescriptors, capabilityType);
    }
    return descriptor;
  }

  @Override
  public CapabilityDescriptor[] getAll() {
    final Collection<CapabilityDescriptor> all = Lists.newArrayList();
    all.addAll(descriptors);
    all.addAll(dynamicDescriptors);

    return all.toArray(new CapabilityDescriptor[all.size()]);
  }

  private CapabilityDescriptor get(final Collection<CapabilityDescriptor> descriptors,
                                   final CapabilityType capabilityType)
  {
    for (final CapabilityDescriptor descriptor : descriptors) {
      if (descriptor.type().equals(capabilityType)) {
        return descriptor;
      }
    }
    return null;
  }

}
