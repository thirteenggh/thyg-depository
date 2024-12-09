package org.sonatype.nexus.repository.content.store.example;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.store.FormatStoreModule;

/**
 * Plain store module for a plain format that uses standard stores with the format's DAOs.
 */
@Named("test")
public class TestPlainStoreModule
    extends FormatStoreModule<TestContentRepositoryDAO,
                              TestComponentDAO,
                              TestAssetDAO,
                              TestAssetBlobDAO>
{
  // nothing to add...
}
