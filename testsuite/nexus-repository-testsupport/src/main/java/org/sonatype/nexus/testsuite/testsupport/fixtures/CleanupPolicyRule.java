package org.sonatype.nexus.testsuite.testsupport.fixtures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Provider;

import org.sonatype.nexus.cleanup.storage.CleanupPolicy;
import org.sonatype.nexus.cleanup.storage.CleanupPolicyStorage;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @since 3.20
 */
public class CleanupPolicyRule
    extends ExternalResource
{
  private static final Logger log = LoggerFactory.getLogger(CleanupPolicyRule.class);

  private final Provider<CleanupPolicyStorage> cleanupPolicyStorageProvider;

  private final List<CleanupPolicy> cleanupPolicies = new ArrayList<>();

  public CleanupPolicyRule(final Provider<CleanupPolicyStorage> cleanupPolicyStorageProvider) {
    this.cleanupPolicyStorageProvider = cleanupPolicyStorageProvider;
  }

  public CleanupPolicy create(final String name, final Map<String, String> criteria) {
    return createCleanupPolicy(name, "format", "mode", criteria);
  }


  public CleanupPolicy createCleanupPolicy(
      final String name,
      final String format,
      final String mode,
      final Map<String, String> criteria)
  {
    CleanupPolicyStorage storage = cleanupPolicyStorageProvider.get();
    CleanupPolicy policy = storage.newCleanupPolicy();
    policy.setName(name);
    policy.setNotes("notes");
    policy.setFormat(format);
    policy.setMode(mode);
    policy.setCriteria(criteria);

    storage.add(policy);
    cleanupPolicies.add(policy);

    return policy;
  }

  @Override
  protected void after() {
    cleanupPolicies.forEach(cleanupPolicy -> {
      try {
        cleanupPolicyStorageProvider.get().remove(cleanupPolicy);
      }
      catch (Exception e) {
        log.error("Failed to remove CleanupPolicy {}", cleanupPolicy, e);
      }
    });
  }
}
