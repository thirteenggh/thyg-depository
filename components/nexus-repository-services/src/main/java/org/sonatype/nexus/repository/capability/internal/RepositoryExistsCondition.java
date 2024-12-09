package org.sonatype.nexus.repository.capability.internal;

import java.util.function.Supplier;

import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.manager.RepositoryCreatedEvent;
import org.sonatype.nexus.repository.manager.RepositoryDeletedEvent;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * A condition that is satisfied when a repository exists.
 *
 * @since 3.0
 */
public class RepositoryExistsCondition
    extends RepositoryConditionSupport
{

  public RepositoryExistsCondition(final EventManager eventManager,
                                   final RepositoryManager repositoryManager,
                                   final Supplier<String> repositoryName)
  {
    super(eventManager, repositoryManager, repositoryName);
  }

  @Override
  @AllowConcurrentEvents
  @Subscribe
  public void handle(final RepositoryCreatedEvent event) {
    if (sameRepositoryAs(event.getRepository().getName())) {
      setSatisfied(true);
    }
  }

  @AllowConcurrentEvents
  @Subscribe
  public void handle(final RepositoryDeletedEvent event) {
    if (sameRepositoryAs(event.getRepository().getName())) {
      setSatisfied(false);
    }
  }

  @Override
  public String toString() {
    try {
      final String repositoryName = getRepositoryName();
      return String.format("Repository '%s' exists", repositoryName);
    }
    catch (Exception ignore) {
      return "Repository '(could not be evaluated)' exists";
    }
  }

  @Override
  public String explainSatisfied() {
    try {
      final String repositoryName = getRepositoryName();
      return String.format("Repository '%s' exists", repositoryName);
    }
    catch (Exception ignore) {
      return "Repository '(could not be evaluated)' exists";
    }
  }

  @Override
  public String explainUnsatisfied() {
    try {
      final String repositoryName = getRepositoryName();
      return String.format("Repository '%s' does not exist", repositoryName);
    }
    catch (Exception ignore) {
      return "Repository '(could not be evaluated)' does not exist";
    }
  }

}
