package org.sonatype.nexus.content.raw.internal.browse;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.browse.store.FormatBrowseModule;
import org.sonatype.nexus.repository.raw.internal.RawFormat;

/**
 * Configures the browse bindings for the raw format.
 *
 * @since 3.26
 */
@Named(RawFormat.NAME)
public class RawBrowseModule
    extends FormatBrowseModule<RawBrowseNodeDAO>
{
  // nothing to add...
}
