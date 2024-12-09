package org.sonatype.nexus.repository.content.npm.internal.store;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.FormatStoreModule;
import org.sonatype.nexus.repository.npm.internal.NpmFormat;

/**
 * Configures the content store bindings for the npm format.
 *
 * @since 3.28
 */
@Named(NpmFormat.NAME)
public class NpmStoreModule
    extends FormatStoreModule<NpmContentRepositoryDAO,
                              NpmComponentDAO,
                              NpmAssetDAO,
                              NpmAssetBlobDAO>
{
  // nothing to add...
}
