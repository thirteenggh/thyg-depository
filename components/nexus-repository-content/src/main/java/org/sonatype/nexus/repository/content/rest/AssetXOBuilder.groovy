package org.sonatype.nexus.repository.content.rest

import javax.annotation.Nullable

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.content.Asset
import org.sonatype.nexus.repository.rest.api.AssetXO
import org.sonatype.nexus.repository.rest.api.AssetXODescriptor
import org.sonatype.nexus.repository.rest.api.RepositoryItemIDXO

import static org.sonatype.nexus.repository.content.store.InternalIds.internalAssetId
import static org.sonatype.nexus.repository.content.store.InternalIds.toExternalId

/**
 * Builds asset transfer objects for REST APIs.
 *
 * @since 3.26
 */
class AssetXOBuilder
{
  static AssetXO fromAsset(final Asset asset, final Repository repository,
                           final Map<String, AssetXODescriptor> assetDescriptors)
  {
    String externalId = toExternalId(internalAssetId(asset)).value

    Map checksum = asset.blob().map({ blob -> blob.checksums() }).orElse([:])

    String contentType = asset.blob().map({ blob -> blob.contentType() }).orElse(null)
    String format = repository.format.value

    return AssetXO.builder()
        .path(asset.path())
        .downloadUrl(repository.url + asset.path())
        .id(new RepositoryItemIDXO(repository.name, externalId).value)
        .repository(repository.name)
        .checksum(checksum)
        .format(format)
        .contentType(contentType)
        .lastModified(calculateLastModified(asset))
        .attributes(getExpandedAttributes(asset, format, assetDescriptors))
        .build()
  }

  private static Date calculateLastModified(final Asset asset) {
    Date lastModified = null
    if (asset.lastUpdated()) {
      lastModified = Date.from(asset.lastUpdated().toInstant())
    }
    else if (asset.created()) {
      lastModified = Date.from(asset.created().toInstant())
    }

    return lastModified
  }

  private static Map getExpandedAttributes(final Asset asset, final String format,
                                           @Nullable final Map<String, AssetXODescriptor> assetDescriptors)
  {
    Set<String> exposedAttributeKeys = assetDescriptors?.get(format)?.listExposedAttributeKeys()
    Map expanded = [:]
    if (exposedAttributeKeys) {
      Map exposedAttributes = (asset.attributes(format)?.backing() as Map<String, Object>)?.subMap(exposedAttributeKeys)
      if (exposedAttributes) {
        expanded.put(format, exposedAttributes)
      }
    }

    return expanded
  }
}
