package org.sonatype.nexus.content.example.internal.store;

import javax.inject.Named;

import org.sonatype.nexus.content.example.internal.recipe.ExampleFormat;
import org.sonatype.nexus.repository.content.store.FormatStoreModule;

/**
 * Configures the content store bindings for an 'example' format.
 *
 * @since 3.24
 */
@Named(ExampleFormat.NAME)
public class ExampleStoreModule
    extends FormatStoreModule<ExampleContentRepositoryDAO,
                              ExampleComponentDAO,
                              ExampleAssetDAO,
                              ExampleAssetBlobDAO>
{
  // nothing to add...
}
