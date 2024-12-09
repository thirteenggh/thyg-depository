package org.sonatype.nexus.testdb.example;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.datastore.api.DataSession;
import org.sonatype.nexus.datastore.api.Expects;
import org.sonatype.nexus.testdb.DataSessionRule;

import org.junit.Rule;
import org.junit.Test;

/**
 * Test the {@link Expects} annotation, used to declare access types to be registered first.
 */
public class ExpectsTest
    extends TestSupport
{
  @Rule
  public DataSessionRule sessionRule = new DataSessionRule().access(TestExpectsDAO.class);

  @SuppressWarnings("java:S2699") // sonar expects assertions, but best to let this exception bubble up
  @Test
  public void testExpectedAccessTypesRegisteredFirst() {
    try (DataSession<?> session = sessionRule.openSession("config")) {
      // will fail if @Expects is not respected
      session.access(TestItemDAO.class);
      session.getTransaction().commit();
    }
  }
}
