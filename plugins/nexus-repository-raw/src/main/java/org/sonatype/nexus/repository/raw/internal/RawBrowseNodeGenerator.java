package org.sonatype.nexus.repository.raw.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.browse.AssetPathBrowseNodeGenerator;

/**
 * RAW places components at the same level as their assets.
 *
 * @since 3.6
 */
@Singleton
@Named(RawFormat.NAME)
public class RawBrowseNodeGenerator
    extends AssetPathBrowseNodeGenerator
{
}
