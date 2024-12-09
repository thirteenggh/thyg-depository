package org.sonatype.nexus.repository.httpbridge;

import org.sonatype.nexus.repository.httpbridge.internal.LegacyViewServlet;

/**
 * Legacy view contributor to {@link LegacyViewServlet}.
 *
 * @since 3.7
 */
public interface LegacyViewContributor
{
  LegacyViewConfiguration contribute();
}
