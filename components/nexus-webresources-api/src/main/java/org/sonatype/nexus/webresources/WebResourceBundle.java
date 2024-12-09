package org.sonatype.nexus.webresources;

import java.util.List;

/**
 * Container for contributed {@link WebResource}.
 *
 * @since 2.8
 */
public interface WebResourceBundle
{
  List<WebResource> getResources();
}
