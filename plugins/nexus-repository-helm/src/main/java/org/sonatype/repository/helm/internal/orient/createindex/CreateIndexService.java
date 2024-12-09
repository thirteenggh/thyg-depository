package org.sonatype.repository.helm.internal.orient.createindex;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * Build index.yaml file for Helm Hosted
 *
 * @since 3.28
 */
public interface CreateIndexService
{
  TempBlob buildIndexYaml(final Repository repository);
}
