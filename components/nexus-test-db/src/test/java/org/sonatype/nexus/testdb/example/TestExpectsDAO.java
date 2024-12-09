package org.sonatype.nexus.testdb.example;

import org.sonatype.nexus.datastore.api.DataAccess;
import org.sonatype.nexus.datastore.api.Expects;

@Expects(TestItemDAO.class)
public interface TestExpectsDAO
    extends DataAccess
{
  // no additional behaviour
}
