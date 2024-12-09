package org.sonatype.nexus.content.maven.internal.browse;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.browse.store.FormatBrowseModule;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

/**
 * Configures the browse bindings for the maven format.
 *
 * @since 3.26
 */
@Named(Maven2Format.NAME)
public class Maven2BrowseModule
    extends FormatBrowseModule<Maven2BrowseNodeDAO>
{
  // nothing to add...
}
