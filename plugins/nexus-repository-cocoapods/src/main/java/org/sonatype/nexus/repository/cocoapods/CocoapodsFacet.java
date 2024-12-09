package org.sonatype.nexus.repository.cocoapods;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Nullable;

import org.sonatype.nexus.repository.Facet;
import org.sonatype.nexus.repository.view.Content;

/**
 * @since 3.19
 */
@Facet.Exposed
public interface CocoapodsFacet
    extends Facet
{
  @Nullable
  Content get(final String assetPath);

  boolean delete(final String assetPath) throws IOException;

  Content getOrCreateAsset(final String assetPath,
                           final Content content,
                           final String componentName,
                           final String componentVersion)
      throws IOException;

  Content getOrCreateAsset(final String assetPath,
                           final Content content,
                           @Nullable final Map<String, String> formatAttributes)
      throws IOException;

  Content getOrCreateAsset(final String assetPath,
                           final Content content)
      throws IOException;

  @Nullable
  <T> T getAssetFormatAttribute(final String assetPath, final String attributeName);
}
