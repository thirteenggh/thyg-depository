package org.sonatype.nexus.repository.content.store.example;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.AssetBlobStore;
import org.sonatype.nexus.repository.content.store.BespokeFormatStoreModule;
import org.sonatype.nexus.repository.content.store.ComponentStore;
import org.sonatype.nexus.repository.content.store.ContentRepositoryStore;

/**
 * Bespoke store module for a bespoke format that uses a custom asset store with extra features.
 */
@Named("test")
public class TestBespokeStoreModule
    extends BespokeFormatStoreModule<ContentRepositoryStore<TestContentRepositoryDAO>,
                                     ComponentStore<TestComponentDAO>,
                                     TestAssetStore, // adds support for browseFlaggedAssets
                                     AssetBlobStore<TestAssetBlobDAO>>
{
  // nothing to add...
}
