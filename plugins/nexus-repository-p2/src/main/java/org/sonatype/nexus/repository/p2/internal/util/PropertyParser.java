package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.sonatype.nexus.repository.p2.internal.exception.AttributeParsingException;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * @since 3.28
 */
public class PropertyParser
{
  private static final String PROPERTY_RESOURCE_BUNDLE_EXTENSION = ".properties";

  private JarExtractor<PropertyResourceBundle> jarExtractor;

  @Inject
  public PropertyParser(final TempBlobConverter tempBlobConverter) {
    jarExtractor = new JarExtractor<PropertyResourceBundle>(tempBlobConverter)
    {
      @Override
      protected PropertyResourceBundle createSpecificEntity(JarInputStream jis, JarEntry jarEntry) throws IOException
      {
          return new PropertyResourceBundle(jis);
      }
    };
  }

  protected Optional<PropertyResourceBundle> getBundleProperties(
      final TempBlob tempBlob,
      final String extension,
      @Nullable final String startNameForSearch) throws IOException, AttributeParsingException
  {

    return jarExtractor.getSpecificEntity(tempBlob, extension, startNameForSearch + PROPERTY_RESOURCE_BUNDLE_EXTENSION);
  }

  protected String extractValueFromProperty(
      final String value,
      final Optional<PropertyResourceBundle> propertyResourceBundleOpt)
  {
    if (!propertyResourceBundleOpt.isPresent() || value == null ||
        !propertyResourceBundleOpt.get().containsKey(value.substring(1))) {
      return value;
    }

    // get property key for bundle name without '%' character in start
    return propertyResourceBundleOpt.get().getString(value.substring(1));
  }
}
