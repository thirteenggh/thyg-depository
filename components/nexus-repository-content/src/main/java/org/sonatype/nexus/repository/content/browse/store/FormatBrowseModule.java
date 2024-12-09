package org.sonatype.nexus.repository.content.browse.store;

import org.sonatype.nexus.repository.content.store.ContentStoreModule;

/**
 * Extend this module to add the necessary bindings to support browsing.
 * Declare your DAO and annotate the module with the name of your format:
 *
 * <code><pre>
 * &#64;Named("example")
 * public class ExampleBrowseModule
 *     extends FormatBrowseModule&lt;ExampleBrowseNodeDAO&gt;
 * {
 *   // nothing to add...
 * }
 * </pre></code>
 *
 * @since 3.26
 */
public abstract class FormatBrowseModule<DAO extends BrowseNodeDAO>
    extends ContentStoreModule<BrowseNodeStore<DAO>>
{
  // nothing to add...
}
