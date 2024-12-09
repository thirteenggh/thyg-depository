package org.sonatype.nexus.testsuite.testsupport;

import org.sonatype.nexus.testsuite.testsupport.fixtures.RepositoryRule;

/**
 * Support class for repository format ITs.
 */
public abstract class RepositoryITSupport
    extends GenericRepositoryITSupport<RepositoryRule>
{
  @Override
  protected RepositoryRule createRepositoryRule() {
    return new RepositoryRule(() -> repositoryManager);
  }
}
