package org.sonatype.repository.helm.internal.content.store;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.FormatStoreModule;
import org.sonatype.repository.helm.internal.HelmFormat;

/**
 * @since 3.28
 */
@Named(HelmFormat.NAME)
public class HelmStoreModule
    extends FormatStoreModule<HelmContentRepositoryDAO,
    HelmComponentDAO,
    HelmAssetDAO,
    HelmAssetBlobDAO>
{
  // nothing to add...
}
