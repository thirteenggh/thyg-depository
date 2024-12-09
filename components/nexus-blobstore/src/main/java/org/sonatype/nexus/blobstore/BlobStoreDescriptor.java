package org.sonatype.nexus.blobstore;

import java.util.List;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;
import org.sonatype.nexus.formfields.FormField;

/**
 * Describes a blob store.
 *
 * @since 3.6
 */
public interface BlobStoreDescriptor
{
  /**
   * A user friendly name of the blob store type to be presented in UI.
   *
   * @return blob store name
   */
  String getName();

  /**
   * Form fields to configure the blob store.
   *
   * @return blob store configuration form fields
   */
  List<FormField> getFormFields();

  /**
   * Name of a custom form for configuring the blob store.
   *
   * @return custom form name
   */
  @Nullable
  default String customFormName() {
    return null;
  }

  /**
   * @return true if the blob store can be modified after creating.
   *
   * @since 3.14
   */
  default boolean isModifiable() {
    return true;
  }

  /**
   * Validate configuration.
   *
   * @since 3.14
   */
  default void validateConfig(BlobStoreConfiguration config) {
  }

  /**
   * Modifies the config to ensure the input is valid
   *
   * @since 3.17
   */
  default void sanitizeConfig(BlobStoreConfiguration config) {
  }

  /**
   * @return true if the blob store type is enabled.
   *
   * @since 3.14
   */
  default boolean isEnabled() {
    return true;
  }

  /**
   * @return true if the configuration has a dependency on another blobstore with the given name
   *
   * @since 3.14
   */
  default boolean configHasDependencyOn(BlobStoreConfiguration config, String blobStoreName) {
    return false;
  }
}
