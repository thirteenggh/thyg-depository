package org.sonatype.nexus.repository.capability.internal;

import org.sonatype.goodies.testsupport.TestSupport;
import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.capability.RepositoryConditions;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * {@link RepositoryConditionsImpl} UTs.
 *
 * @since capabilities 2.0
 */
public class RepositoryConditionsImplTest
    extends TestSupport
{

  private RepositoryConditions underTest;

  @Before
  public void setUpRepositoryConditions() {
    final EventManager eventManager = mock(EventManager.class);
    underTest = new RepositoryConditionsImpl(eventManager, mock(RepositoryManager.class));
  }

  /**
   * repositoryIsInService() factory method returns expected condition.
   */
  @Test
  public void repositoryIsInService() {
    assertThat(
        underTest.repositoryIsOnline(() -> "repo-name"),
        is(Matchers.<Condition>instanceOf(RepositoryOnlineCondition.class))
    );
  }

  /**
   * repositoryExists() factory method returns expected condition.
   */
  @Test
  public void repositoryExists() {
    assertThat(
        underTest.repositoryExists(() -> "repo-name"),
        is(Matchers.<Condition>instanceOf(RepositoryExistsCondition.class))
    );
  }

}
