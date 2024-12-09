package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.Condition;

import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * {@link NexusConditionsImpl} UTs.
 *
 * @since capabilities 2.0
 */
public class NexusConditionsImplTest
    extends TestSupport
{

  /**
   * active() factory method returns expected condition.
   */
  @Test
  public void active() {
    final NexusIsActiveCondition nexusIsActiveCondition = mock(NexusIsActiveCondition.class);
    final NexusConditionsImpl underTest = new NexusConditionsImpl(nexusIsActiveCondition);

    assertThat(
        underTest.active(),
        is(Matchers.<Condition>instanceOf(NexusIsActiveCondition.class))
    );
  }

}
