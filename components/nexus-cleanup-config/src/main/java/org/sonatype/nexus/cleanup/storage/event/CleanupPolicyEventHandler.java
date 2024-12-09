package org.sonatype.nexus.cleanup.storage.event;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventAware.Asynchronous;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.manager.RepositoryManager;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.Objects.nonNull;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.CLEANUP_ATTRIBUTES_KEY;
import static org.sonatype.nexus.cleanup.config.CleanupPolicyConstants.CLEANUP_NAME_KEY;

/**
 * Event handler for {@link CleanupPolicy}'s
 *
 * @since 3.14
 */
@Singleton
@Named
public class CleanupPolicyEventHandler
    extends ComponentSupport
    implements EventAware, Asynchronous
{
  private final RepositoryManager repositoryManager;

  @Inject
  public CleanupPolicyEventHandler(final RepositoryManager repositoryManager) {
    this.repositoryManager = checkNotNull(repositoryManager);
  }

  @Subscribe
  @AllowConcurrentEvents
  public void on(final CleanupPolicyDeletedEvent event) {
    if (shouldProcess(event)) {

      CleanupPolicy cleanupPolicy = event.getCleanupPolicy();

      repositoryManager
          .browseForCleanupPolicy(cleanupPolicy.getName())
          .forEach(repository -> remove(repository.getConfiguration().copy(), cleanupPolicy));
    }
  }


  private void remove(final Configuration configuration, final CleanupPolicy cleanupPolicy) {
    try {
      removeFromConfigurationIfHasCleanupPolicy(configuration, cleanupPolicy);

      repositoryManager.update(configuration);

      log.info("Removed Cleanup Policy {} from Repository {}",
          cleanupPolicy.getName(), configuration.getRepositoryName());
    }
    catch (Exception e) {
      // this should not occur
      log.error("Unable to remove Cleanup Policy {} from Repository {}",
          cleanupPolicy.getName(), configuration.getRepositoryName(), e);

      throw new RuntimeException(e);
    }
  }

  private boolean shouldProcess(final CleanupPolicyEvent event) {
    checkNotNull(event);
    return event.isLocal();
  }

  private void removeFromConfigurationIfHasCleanupPolicy(final Configuration configuration,
                                                         final CleanupPolicy cleanupPolicy)
  {
    Map<String, Map<String, Object>> attributes = configuration.getAttributes();
    if (nonNull(attributes)) {
      Map<String, Object> cleanup = attributes.get(CLEANUP_ATTRIBUTES_KEY);
      if (nonNull(cleanup)) {
        removeAttributeOrRemoveFromCleanupIfCleanupPolicyPresent(attributes, cleanup, cleanupPolicy);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void removeAttributeOrRemoveFromCleanupIfCleanupPolicyPresent(final Map<String, Map<String, Object>> attributes,
                                                                        final Map<String, Object> cleanup,
                                                                        final CleanupPolicy cleanupPolicy)
  {
    Set<String> policyNames = new HashSet<>((Set<String>) cleanup.get(CLEANUP_NAME_KEY));
    if (policyNames.removeIf(policyName -> policyName.equals(cleanupPolicy.getName()))) {
      // if we removed the last one, remove the cleanup attribute completely
      if (policyNames.isEmpty()) {
        attributes.remove(CLEANUP_ATTRIBUTES_KEY);
      }
      else {
        cleanup.put(CLEANUP_NAME_KEY, policyNames);
      }
    }
  }
}
