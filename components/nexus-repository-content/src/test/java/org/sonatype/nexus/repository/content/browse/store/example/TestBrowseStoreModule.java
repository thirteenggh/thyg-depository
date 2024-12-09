package org.sonatype.nexus.repository.content.browse.store.example;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.browse.store.FormatBrowseModule;

/**
 * Browse module for our test format.
 */
@Named("test")
public class TestBrowseStoreModule
    extends FormatBrowseModule<TestBrowseNodeDAO>
{
  // nothing to add...
}
