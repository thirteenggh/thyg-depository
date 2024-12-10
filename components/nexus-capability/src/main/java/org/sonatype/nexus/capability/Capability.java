package org.sonatype.nexus.capability;

import javax.annotation.Nullable;

/**
 * A capability is a stateful representation of configuration and lifecycle exposed for generalized management.
 */
public interface Capability
{
  /**
   * Initializes the capability after it has been created by factory.
   *
   * @param context capability context
   */
  void init(CapabilityContext context);

  /**
   * Returns description of capability.
   *
   * @return description. Can be null.
   */
  @Nullable
  String description();

  /**
   * Returns status of capability.
   *
   * @return status. Can be null. Can be an html chunk.
   */
  @Nullable
  String status();

  /**
   * Callback when a new capability is created.
   * <p/>
   * If an exception occurs, during invocation of this method,  the exception will be ignored and capability will be
   * in an invalid state.
   * Any further interaction with this capability will result in an {@link IllegalStateException}.
   *
   * @throws Exception If capability cannot be create
   */
  void onCreate() throws Exception;

  /**
   * Callback when a capability configuration is loaded from persisted store (configuration file).
   * <p/>
   * If an exception occurs, during invocation of this method,  the exception will be ignored and capability will be
   * in an invalid state.
   * Any further interaction with this capability will result in an {@link IllegalStateException}.
   *
   * @throws Exception If capability cannot be loaded
   */
  void onLoad() throws Exception;

  /**
   * Callback when a capability configuration is updated.
   * <p/>
   * If an exception occurs, during invocation of this method, the exception will be ignored and capability, if
   * active, will be automatically passivated.
   *
   * @throws Exception If capability cannot be updated
   */
  void onUpdate() throws Exception;

  /**
   * Callback when a capability is removed.
   * <p/>
   * If an exception occurs, during invocation of this method, the exception will be ignored and capability will be
   * in
   * a removed state.
   *
   * @throws Exception If capability cannot be removed
   */
  void onRemove() throws Exception;

  /**
   * Callback when capability is activated. Activation is triggered on create/load (if capability is not disabled),
   * or when capability is re-enabled.
   * <p/>
   * If an exception occurs, during invocation of this method, the exception will be ignored and capability will be
   * in
   * an non active state.
   *
   * @throws Exception If capability cannot be activated
   */
  void onActivate() throws Exception;

  void onPassivate() throws Exception;

  /**
   * Returns the condition that should be satisfied in order for this capability to be active.
   * <p/>
   * If an exception occurs, during invocation of this method, the capability is considered as not activatable.
   *
   * @return activation condition. If null, it considers that condition is always activatable.
   */
  @Nullable
  Condition activationCondition();

  /**
   * Returns the condition that should be satisfied in order for this capability to be valid. When this condition
   * becomes unsatisfied, the capability will be automatically removed.
   * <p/>
   * Example of such a condition will be a capability that applies to a repository should be automatically be removed
   * when repository is removed.
   * <p/>
   * If an exception occurs, during invocation of this method, the capability is considered as always valid.
   *
   * @return activation condition. If null, it considers that condition is always valid.
   */
  @Nullable
  Condition validityCondition();

  /**
   * Returns whether the property with the given name is a password and should not be passed to the UI in cleartext.
   *
   * @return true if the named property is a password
   *
   * @since 3.2
   */
  boolean isPasswordProperty(String propertyName);
}
