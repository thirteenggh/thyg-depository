package org.sonatype.nexus.blobstore.group;

import java.util.List;

import org.sonatype.nexus.blobstore.api.BlobStoreConfiguration;

import static org.sonatype.nexus.blobstore.group.BlobStoreGroup.CONFIG_KEY;
import static org.sonatype.nexus.blobstore.group.BlobStoreGroup.FILL_POLICY_KEY;
import static org.sonatype.nexus.blobstore.group.BlobStoreGroup.MEMBERS_KEY;

/**
 * Helper for blob store group attributes in {@link BlobStoreConfiguration}.
 *
 * @since 3.14
 */
public class BlobStoreGroupConfigurationHelper {

  private BlobStoreGroupConfigurationHelper() {
    // Don't instantiate
  }

  public static List<String> memberNames(final BlobStoreConfiguration configuration) {
    return configuration.attributes(CONFIG_KEY).require(MEMBERS_KEY, List.class);
  }

  public static String fillPolicyName(final BlobStoreConfiguration configuration) {
    return configuration.attributes(CONFIG_KEY).require(FILL_POLICY_KEY).toString();
  }
}
