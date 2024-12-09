package org.sonatype.nexus.repository.capability.internal;

import java.util.function.Supplier;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.Condition;
import org.sonatype.nexus.common.event.EventManager;
import org.sonatype.nexus.repository.capability.RepositoryConditions;
import org.sonatype.nexus.repository.capability.internal.RepositoryExistsCondition;
import org.sonatype.nexus.repository.capability.internal.RepositoryOnlineCondition;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default implementation of {@link RepositoryConditions}.
 *
 * @since 3.0
 */
@Named
@Singleton
public class RepositoryConditionsImpl
    implements RepositoryConditions
{
  private final EventManager eventManager;

  private final RepositoryManager repositoryManager;

  @Inject
  public RepositoryConditionsImpl(final EventManager eventManager, 
                                  final RepositoryManager repositoryManager)  {
    this.eventManager = checkNotNull(eventManager);
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Override
  public Condition repositoryIsOnline(final Supplier<String> repositoryName) {
    return new RepositoryOnlineCondition(eventManager, repositoryManager, repositoryName);
  }

  @Override
  public Condition repositoryExists(final Supplier<String> repositoryName) {
    return new RepositoryExistsCondition(eventManager, repositoryManager, repositoryName);
  }
}
