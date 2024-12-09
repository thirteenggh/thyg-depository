package org.sonatype.repository.helm.internal.content.createindex;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.Content;

/**
 * Build index.yaml file for Helm Hosted
 *
 * @since 3.28
 */
public interface CreateIndexService
{
  Content buildIndexYaml(final Repository repository);
}
