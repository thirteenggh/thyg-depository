package org.sonatype.nexus.repository.rest.cma

import javax.annotation.Nullable

import org.sonatype.nexus.repository.Repository
import org.sonatype.nexus.repository.rest.api.AssetXO
import org.sonatype.nexus.repository.rest.api.AssetXODescriptor
import org.sonatype.nexus.repository.rest.api.RepositoryItemIDXO
import org.sonatype.nexus.repository.storage.Asset

import groovy.transform.CompileStatic

import static org.sonatype.nexus.common.entity.EntityHelper.id
import static org.sonatype.nexus.repository.storage.Asset.CHECKSUM

/**
 * Builds asset transfer objects for REST APIs.
 *
 * @since 3.22
 */
@CompileStatic
class AssetXOBuilder
{
  static AssetXO fromAsset(final Asset asset, final Repository repository,
                           @Nullable final Map<String, AssetXODescriptor> assetDescriptors)
  {
    String internalId = id(asset).getValue()
    Map checksum = asset.attributes().child(CHECKSUM).backing()
    String format = repository.format.value

    return AssetXO.builder()
        .path(asset.name())
        .downloadUrl(repository.url + '/' + asset.name())
        .id(new RepositoryItemIDXO(repository.name, internalId).value)
        .repository(repository.name)
        .checksum(checksum)
        .format(format)
        .contentType(asset.contentType())
        .attributes(getExpandedAttributes(asset, format, assetDescriptors))
        .lastModified(calculateLastModified(asset))
        .build()
  }

  private static Date calculateLastModified(final Asset asset) {
    Date lastModified = asset.blobUpdated()?.toDate()
    if (!lastModified) {
      lastModified = asset.blobCreated()?.toDate()
    }

    return lastModified
  }

  private static Map getExpandedAttributes(final Asset asset, final String format,
                                   @Nullable final Map<String, AssetXODescriptor> assetDescriptors)
  {
    Map expanded = [:]
    expanded["blobCreated"] = asset.blobCreated()?.toDate()
    expanded["lastDownloaded"] = asset.lastDownloaded()?.toDate()

    Set<String> exposedAttributeKeys = assetDescriptors?.get(format)?.listExposedAttributeKeys()

    if (exposedAttributeKeys) {
      Map exposedAttributes = asset.attributes()?.child(format)?.backing()?.subMap(exposedAttributeKeys)
      if (exposedAttributes) {
        expanded.put(format, exposedAttributes)
      }
    }

    return expanded
  }
}
