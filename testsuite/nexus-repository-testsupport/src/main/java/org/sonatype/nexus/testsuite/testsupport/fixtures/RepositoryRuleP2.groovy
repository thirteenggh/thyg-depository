package org.sonatype.nexus.testsuite.testsupport.fixtures

import javax.inject.Provider

import org.sonatype.nexus.repository.manager.RepositoryManager
import org.sonatype.nexus.testsuite.testsupport.fixtures.RepositoryRule

class RepositoryRuleP2
    extends RepositoryRule
    implements P2RepoRecipes
{
  RepositoryRuleP2(final Provider<RepositoryManager> repositoryManagerProvider) {
    super(repositoryManagerProvider)
  }
}
