package org.sonatype.nexus.repository.p2.internal.util;

import java.io.IOException;
import java.util.Optional;
import java.util.PropertyResourceBundle;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.p2.internal.exception.AttributeParsingException;
import org.sonatype.nexus.repository.p2.internal.metadata.P2Attributes;
import org.sonatype.nexus.repository.p2.internal.metadata.P2Attributes.Builder;
import org.sonatype.nexus.repository.view.payloads.TempBlob;

/**
 * @since 3.28
 */
@Named
@Singleton
public class AttributesParserManifest
    implements AttributesParser
{
  private static final String MANIFEST_FILE_PREFIX = "META-INF/";

  private static final String BUNDLE_PROPERTIES = "OSGI-INF/l10n/bundle";

  private JarExtractor<Manifest> manifestJarExtractor;

  private PropertyParser propertyParser;

  @Inject
  public AttributesParserManifest(final TempBlobConverter tempBlobConverter, final PropertyParser propertyParser) {
    this.propertyParser = propertyParser;
    manifestJarExtractor = new JarExtractor<Manifest>(tempBlobConverter)
    {
      @Override
      protected Manifest createSpecificEntity(final JarInputStream jis, final JarEntry jarEntry) throws IOException
      {
        Manifest manifest = jis.getManifest();
        if (manifest != null) {
          return manifest;
        }
        return new Manifest(jis);
      }
    };
  }

  @Override
  public P2Attributes getAttributesFromBlob(final TempBlob tempBlob, final String extension)
      throws IOException, AttributeParsingException
  {
    Builder p2AttributesBuilder = P2Attributes.builder();
    Optional<Manifest> manifestJarEntity =
        manifestJarExtractor.getSpecificEntity(tempBlob, extension, MANIFEST_FILE_PREFIX);
    if (manifestJarEntity.isPresent()) {
      Attributes mainManifestAttributes = manifestJarEntity.get().getMainAttributes();
      String bundleLocalizationValue = mainManifestAttributes.getValue("Bundle-Localization");
      Optional<PropertyResourceBundle> propertiesOpt =
          propertyParser.getBundleProperties(tempBlob, extension,
              bundleLocalizationValue == null ? BUNDLE_PROPERTIES : bundleLocalizationValue);

      p2AttributesBuilder
          .componentName(normalizeName(propertyParser
              .extractValueFromProperty(mainManifestAttributes.getValue("Bundle-SymbolicName"), propertiesOpt)))
          .pluginName(
              propertyParser.extractValueFromProperty(mainManifestAttributes.getValue("Bundle-Name"), propertiesOpt))
          .componentVersion(
              propertyParser
                  .extractValueFromProperty(mainManifestAttributes.getValue("Bundle-Version"), propertiesOpt));
    }

    return p2AttributesBuilder.build();
  }

  private String normalizeName(final String name) {
    String resultName = name;
    //handle org.tigris.subversion.clientadapter.svnkit;singleton:=true
    if (name != null) {
      resultName = name.split(";")[0];
    }
    return resultName;
  }
}
