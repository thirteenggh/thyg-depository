package org.sonatype.nexus.content.raw.internal.store;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.FormatStoreModule;
import org.sonatype.nexus.repository.raw.internal.RawFormat;

/**
 * Configures the content store bindings for the raw format.
 *
 * @since 3.24
 */
@Named(RawFormat.NAME)
public class RawStoreModule
    extends FormatStoreModule<RawContentRepositoryDAO,
                              RawComponentDAO,
                              RawAssetDAO,
                              RawAssetBlobDAO>
{
  // nothing to add...
}
