package org.sonatype.nexus.content.raw.internal.browse;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.browse.AssetPathBrowseNodeGenerator;
import org.sonatype.nexus.repository.raw.internal.RawFormat;

/**
 * RAW places components at the same level as their assets.
 *
 * @since 3.26
 */
@Singleton
@Named(RawFormat.NAME)
public class RawBrowseNodeGenerator
    extends AssetPathBrowseNodeGenerator
{
}
