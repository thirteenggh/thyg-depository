
package org.sonatype.nexus.content.maven.store;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.AssetBlobStore;
import org.sonatype.nexus.repository.content.store.AssetStore;
import org.sonatype.nexus.repository.content.store.BespokeFormatStoreModule;
import org.sonatype.nexus.repository.content.store.ContentRepositoryStore;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

/**
 * Configures the content store bindings for a maven format.
 *
 * @since 3.25
 */
@Named(Maven2Format.NAME)
public class Maven2StoreModule
    extends BespokeFormatStoreModule<ContentRepositoryStore<Maven2ContentRepositoryDAO>,
                                     Maven2ComponentStore, // adds support for the base_version column
                                     AssetStore<Maven2AssetDAO>,
                                     AssetBlobStore<Maven2AssetBlobDAO>>
{
  // nothing to add...
}
