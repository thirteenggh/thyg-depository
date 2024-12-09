package org.sonatype.nexus.repository.search.query;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.Repository;
import org.sonatype.nexus.repository.group.GroupFacet;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import static org.sonatype.nexus.repository.search.index.SearchConstants.REPOSITORY_NAME;

/**
 * @since 3.15.2
 */
@Named(RepositorySearchContribution.NAME)
@Singleton
public class RepositorySearchContribution
    extends DefaultSearchContribution
{
  public static final String NAME = REPOSITORY_NAME;

  private final RepositoryManager repositoryManager;

  @Inject
  public RepositorySearchContribution(final RepositoryManager repositoryManager) {
    this.repositoryManager = repositoryManager;
  }

  @Override
  public void contribute(final BoolQueryBuilder query, final String type, final String value) {
    if (value == null) {
      return;
    }

    Repository repository = repositoryManager.get(value);

    if (repository != null) {
      Optional<GroupFacet> groupFacet = repository.optionalFacet(GroupFacet.class);
      if (groupFacet.isPresent()) {
        List<Repository> members = groupFacet.get().leafMembers();

        query.must(QueryBuilders.termsQuery(type, members.stream().map(Repository::getName).toArray(String[]::new)));
        return;
      }
    }

    super.contribute(query, type, value);
  }
}
