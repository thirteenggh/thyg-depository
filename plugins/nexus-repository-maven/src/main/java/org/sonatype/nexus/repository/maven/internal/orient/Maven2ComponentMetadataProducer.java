package org.sonatype.nexus.repository.maven.internal.orient;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.search.ComponentMetadataProducerExtension;
import org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

import static org.sonatype.nexus.repository.maven.internal.Attributes.P_BASE_VERSION;
import static org.sonatype.nexus.repository.maven.internal.Constants.SNAPSHOT_VERSION_SUFFIX;

/**
 * Maven implementation of {@link DefaultComponentMetadataProducer}
 *
 * @since 3.14
 */
@Singleton
@Named(Maven2Format.NAME)
public class Maven2ComponentMetadataProducer
    extends DefaultComponentMetadataProducer
{
  @Inject
  public Maven2ComponentMetadataProducer(final Set<ComponentMetadataProducerExtension> extensions) {
    super(extensions);
  }

  @Override
  protected boolean isPrerelease(final Component component, final Iterable<Asset> assets) {
    String baseVersion = (String) component.attributes().child(Maven2Format.NAME).get(P_BASE_VERSION);
    if(baseVersion == null) {
      return false;
    }
    return baseVersion.endsWith(SNAPSHOT_VERSION_SUFFIX);
  }
}

