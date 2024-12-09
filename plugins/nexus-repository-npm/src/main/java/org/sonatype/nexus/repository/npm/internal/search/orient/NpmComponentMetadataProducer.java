package org.sonatype.nexus.repository.npm.internal.search.orient;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.npm.internal.NpmFormat;
import org.sonatype.nexus.repository.search.ComponentMetadataProducerExtension;
import org.sonatype.nexus.repository.search.DefaultComponentMetadataProducer;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.Component;

/**
 * Npm implementation of {@link DefaultComponentMetadataProducer}
 *
 * @since 3.14
 */
@Singleton
@Named(NpmFormat.NAME)
public class NpmComponentMetadataProducer
    extends DefaultComponentMetadataProducer
{
  @Inject
  public NpmComponentMetadataProducer(final Set<ComponentMetadataProducerExtension> extensions) {
    super(extensions);
  }

  @Override
  protected boolean isPrerelease(final Component component, final Iterable<Asset> assets) {
    return component.version().contains("-");
  }
}
