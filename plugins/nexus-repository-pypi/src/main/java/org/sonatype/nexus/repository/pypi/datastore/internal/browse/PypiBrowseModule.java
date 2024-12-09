package org.sonatype.nexus.repository.pypi.datastore.internal.browse;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.browse.store.FormatBrowseModule;
import org.sonatype.nexus.repository.pypi.internal.PyPiFormat;

/**
 * @since 3.29
 */
@Named(PyPiFormat.NAME)
public class PypiBrowseModule
    extends FormatBrowseModule<PypiBrowseNodeDAO>
{
}
