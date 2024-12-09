package org.sonatype.nexus.testsuite.testsupport.pypi;

import org.sonatype.nexus.repository.Repository;

public interface PyPiTestHelper
{
  void deleteComponent(final Repository repository, final String name, final String version);

  boolean isRootIndexExist(final Repository repository);
}
