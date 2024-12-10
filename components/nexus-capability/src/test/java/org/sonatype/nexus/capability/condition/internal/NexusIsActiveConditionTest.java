package org.sonatype.nexus.capability.condition.internal;

import org.sonatype.nexus.capability.condition.EventManagerTestSupport;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * {@link NexusIsActiveCondition} UTs.
 *
 * @since capabilities 2.0
 */
public class NexusIsActiveConditionTest
    extends EventManagerTestSupport
{

  private NexusIsActiveCondition underTest;

  @Before
  public final void setUpNexusIsActiveCondition() throws Exception {
    underTest = new NexusIsActiveCondition(eventManager);
  }

  /**
   * Condition is not satisfied initially.
   */
  @Test
  public void notSatisfiedInitially() {
    assertThat(underTest.isSatisfied(), is(false));
  }

  @Test
  public void satisfiedWhenNexusStarted() {
    underTest.start();
    assertThat(underTest.isSatisfied(), is(true));

    verifyEventManagerEvents(satisfied(underTest));
  }

  /**
   * Condition is satisfied when negated is not satisfied.
   */
  @Test
  public void unsatisfiedWhenNexusStopped() {
    underTest.start();
    underTest.stop();
    assertThat(underTest.isSatisfied(), is(false));

    verifyEventManagerEvents(satisfied(underTest), unsatisfied(underTest));
  }

}
