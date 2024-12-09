package org.sonatype.nexus.repository.content.upload;

import java.util.Optional;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.entity.EntityId;
import org.sonatype.nexus.repository.content.Asset;
import org.sonatype.nexus.repository.content.Component;
import org.sonatype.nexus.repository.content.store.InternalIds;
import org.sonatype.nexus.repository.upload.UploadProcessor;
import org.sonatype.nexus.repository.view.Content;

/**
 * Content implementation for {@link UploadProcessor}
 *
 * @since 3.29
 */
@Named
@Singleton
public class UploadComponentProcessor
    implements UploadProcessor
{
  /**
   * Extract {@link EntityId} of {@link Component} from {@link Content}
   *
   * @param content uploaded {@link Content} with {@link Asset} in attributes
   * @return {@link EntityId} of {@link Component}
   */
  @Override
  public Optional<EntityId> extractId(final Content content) {
    Optional<Asset> asset = Optional.ofNullable(content.getAttributes().get(Asset.class));
    return asset.flatMap(Asset::component)
        .map(component -> InternalIds.toExternalId(InternalIds.internalComponentId(component)));
  }
}
