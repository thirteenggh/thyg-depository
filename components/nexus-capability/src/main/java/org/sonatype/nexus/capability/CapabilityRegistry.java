package org.sonatype.nexus.capability;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

/**
 * Registry of current configured capabilities.
 */
public interface CapabilityRegistry
{

  /**
   * Creates a new capability.
   *
   * @param type       type of capability to be created
   * @param enabled    whether or not created capability should be enabled
   * @param notes      optional capability notes (can be null)
   * @param properties optional capability properties (can be null)
   * @return reference to created capability (never null)
   */
  CapabilityReference add(CapabilityType type,
                          boolean enabled,
                          @Nullable String notes,
                          @Nullable Map<String, String> properties);

  /**
   * Updates a capability.
   *
   * @param id         of capability to be updated
   * @param enabled    whether or not updated capability should be enabled
   * @param notes      optional capability notes (can be null)
   * @param properties optional capability properties (can be null)
   * @return reference to updated capability (never null)
   * @throws CapabilityNotFoundException If capability with specified id does not exist
   */
  CapabilityReference update(CapabilityIdentity id,
                             boolean enabled,
                             @Nullable String notes,
                             @Nullable Map<String, String> properties);

  /**
   * Removes a capability.
   *
   * @param id of capability to be removed
   * @return reference of removed capability (never null)
   * @throws CapabilityNotFoundException If capability with specified id does not exist
   */
  CapabilityReference remove(CapabilityIdentity id);

  /**
   * Enables a capability.
   *
   * @param id of capability to be enabled
   * @return reference to enabled capability (never null)
   * @throws CapabilityNotFoundException If capability with specified id does not exist
   */
  CapabilityReference enable(CapabilityIdentity id);

  /**
   * Disables a capability.
   *
   * @param id of capability to be disabled
   * @return reference to disabled capability
   * @throws CapabilityNotFoundException If capability with specified id does not exist
   */
  CapabilityReference disable(CapabilityIdentity id);

  /**
   * Retrieves the capability from registry with specified id. If there is no capability with specified id in the
   * registry it will return null.
   *
   * @param id to retrieve
   * @return capability with specified id or null if not found
   * @since capabilities 2.0
   */
  CapabilityReference get(CapabilityIdentity id);

  /**
   * Retrieves all capabilities from registry that matches the specified filter. If no capability exists or matches,
   * result will be empty.
   *
   * @param filter capability reference filter
   * @return collection of capabilities, never null
   * @since capabilities 2.0
   */
  Collection<? extends CapabilityReference> get(Predicate<CapabilityReference> filter);

  /**
   * Retrieves all capabilities from registry. If no capability exists, result will be empty.
   *
   * @return collection of capabilities, never null
   * @since capabilities 2.0
   */
  Collection<? extends CapabilityReference> getAll();

}
