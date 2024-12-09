package org.sonatype.nexus.datastore;

import java.util.List;

import org.sonatype.nexus.datastore.api.DataStore;
import org.sonatype.nexus.datastore.api.DataStoreConfiguration;
import org.sonatype.nexus.formfields.FormField;

/**
 * Describes a {@link DataStore} type.
 *
 * @since 3.19
 */
public interface DataStoreDescriptor
{
  /**
   * A user friendly name of the data store type to be presented in the UI.
   */
  String getName();

  /**
   * Form fields to configure the data store.
   */
  List<FormField<?>> getFormFields();

  /**
   * Validate the given configuration.
   */
  default void validate(DataStoreConfiguration configuration) {
    // no validation
  }

  /**
   * Is this data store type available for use?
   */
  default boolean isEnabled() {
    return true;
  }
}
