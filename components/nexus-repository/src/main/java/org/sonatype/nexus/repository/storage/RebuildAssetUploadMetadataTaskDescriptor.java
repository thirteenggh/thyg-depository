package org.sonatype.nexus.repository.storage;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.node.NodeAccess;
import org.sonatype.nexus.scheduling.TaskDescriptorSupport;

/**
 * Task descriptor for {@link RebuildAssetUploadMetadataTask}.
 *
 * @since 3.6
 */
@Named
@Singleton
public class RebuildAssetUploadMetadataTaskDescriptor
    extends TaskDescriptorSupport
{
  public static final String TYPE_ID = "rebuild.asset.uploadMetadata";

  @Inject
  public RebuildAssetUploadMetadataTaskDescriptor(final NodeAccess nodeAccess,
                                                  final RebuildAssetUploadMetadataConfiguration configuration) {
    super(
        TYPE_ID,
        RebuildAssetUploadMetadataTask.class,
        "Repair - Reconcile date metadata from blob store",
        configuration.isEnabled() ? VISIBLE : NOT_VISIBLE,
        configuration.isEnabled() ? EXPOSED : NOT_EXPOSED,
        nodeAccess.isClustered() ? newLimitNodeFormField() : null);
  }
}
