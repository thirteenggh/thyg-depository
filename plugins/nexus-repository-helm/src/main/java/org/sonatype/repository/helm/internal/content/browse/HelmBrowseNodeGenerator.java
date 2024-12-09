package org.sonatype.repository.helm.internal.content.browse;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.browse.DefaultBrowseNodeGenerator;
import org.sonatype.repository.helm.internal.HelmFormat;

/**
 * Helm places components one level above their assets.
 *
 * @since 3.28
 */
@Singleton
@Named(HelmFormat.NAME)
public class HelmBrowseNodeGenerator
    extends DefaultBrowseNodeGenerator
{
}
