package org.sonatype.repository.helm.internal.content.browse;

import javax.inject.Named;

import org.sonatype.nexus.repository.content.browse.store.FormatBrowseModule;
import org.sonatype.repository.helm.internal.HelmFormat;

/**
 * Configures the browse bindings for the helm format.
 *
 * @since 3.28
 */
@Named(HelmFormat.NAME)
public class HelmBrowseModule
    extends FormatBrowseModule<HelmBrowseNodeDAO>
{
}
