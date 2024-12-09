package org.sonatype.nexus.repository.npm.internal.tasks.orient;

import javax.annotation.Priority;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.collect.ImmutableNestedAttributesMap;
import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.attributes.AttributesFacet;
import org.sonatype.nexus.repository.npm.internal.tasks.ReindexNpmRepositoryManager.UnprocessedRepositoryChecker;

import static java.lang.Boolean.TRUE;
import static org.sonatype.nexus.repository.npm.internal.tasks.orient.OrientReindexNpmRepositoryTask.NPM_V1_SEARCH_UNSUPPORTED;

/**
 * @since 3.27
 */
@Named
@Singleton
@Priority(Integer.MAX_VALUE)
public class OrientUnprocessedRepositoryChecker
    implements UnprocessedRepositoryChecker
{
  @Override
  public boolean isUnprocessedNpmRepository(final Repository repository) {
    AttributesFacet attributesFacet = repository.facet(AttributesFacet.class);
    ImmutableNestedAttributesMap attributes = attributesFacet.getAttributes();
    return TRUE.equals(attributes.get(NPM_V1_SEARCH_UNSUPPORTED));
  }
}
