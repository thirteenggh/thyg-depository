package org.sonatype.nexus.testdb.example;

import org.sonatype.nexus.datastore.api.IterableDataAccess;

public interface TestItemDAO
    extends IterableDataAccess<TestItem>
{
  // no additional behaviour
}
