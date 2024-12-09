package org.sonatype.nexus.capability;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidatorContext;

import org.sonatype.nexus.validation.ConstraintValidatorSupport;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link CapabilityTypeExists} validator.
 *
 * @since 3.0
 */
@Named
public class CapabilityTypeExistsValidator
    extends ConstraintValidatorSupport<CapabilityTypeExists, String>
{
  private final CapabilityFactoryRegistry capabilityFactoryRegistry;

  private final CapabilityDescriptorRegistry capabilityDescriptorRegistry;

  @Inject
  public CapabilityTypeExistsValidator(final CapabilityFactoryRegistry capabilityFactoryRegistry,
                                       final CapabilityDescriptorRegistry capabilityDescriptorRegistry)
  {
    this.capabilityFactoryRegistry = checkNotNull(capabilityFactoryRegistry);
    this.capabilityDescriptorRegistry = checkNotNull(capabilityDescriptorRegistry);
  }

  @Override
  public boolean isValid(final String value, final ConstraintValidatorContext context) {
    if (value != null) {
      log.trace("Validating capability type exists: {}", value);
      CapabilityType type = CapabilityType.capabilityType(value);
      return capabilityFactoryRegistry.get(type) != null && capabilityDescriptorRegistry.get(type) != null;
    }
    return true;
  }
}
