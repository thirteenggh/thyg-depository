package org.sonatype.nexus.repository.content.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.sonatype.nexus.repository.content.RepositoryContent;
import org.sonatype.nexus.repository.content.fluent.FluentAsset;
import org.sonatype.nexus.repository.content.fluent.FluentAttributes;
import org.sonatype.nexus.repository.content.fluent.FluentComponent;

/**
 * @since 3.29
 */
public class FormatAttributesUtils
{
  public static void setFormatAttributes(final FluentAsset fluent, final Map<String, Object> values) {
    setFormatAttributes(fluent.repository().getFormat().getValue(), fluent, fluent, values);
  }

  public static void setFormatAttributes(final FluentAsset fluent, final Supplier<Map<String, Object>> supplier) {
    setFormatAttributes(fluent, supplier.get());
  }

  public static void setFormatAttributes(final FluentAsset fluent, final String key, final Object value) {
    setFormatAttributes(
        fluent,
        new HashMap<String, Object>()
        {{
          put(key, value);
        }}
    );
  }

  public static void setFormatAttributes(final FluentComponent fluent, final Map<String, Object> values) {
    setFormatAttributes(fluent.repository().getFormat().getValue(), fluent, fluent, values);
  }

  private static void setFormatAttributes(
      final String formatName,
      final RepositoryContent repositoryContent,
      final FluentAttributes attributes,
      final Map<String, Object> values)
  {
    @SuppressWarnings("unchecked")
    Map<String, Object> formatAttributes = (Map<String, Object>) repositoryContent
        .attributes()
        .get(formatName, new HashMap<>());
    formatAttributes.putAll(values);
    attributes.withAttribute(formatName, formatAttributes);
  }
}
