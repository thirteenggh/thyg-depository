package org.sonatype.nexus.content.maven.internal;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.repository.content.fluent.FluentComponent;
import org.sonatype.nexus.repository.content.search.DefaultSearchDocumentProducer;
import org.sonatype.nexus.repository.content.search.SearchDocumentExtension;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;

import static org.sonatype.nexus.repository.maven.internal.Attributes.P_BASE_VERSION;
import static org.sonatype.nexus.repository.maven.internal.Constants.SNAPSHOT_VERSION_SUFFIX;

/**
 * Maven implementation of {@link DefaultSearchDocumentProducer}
 *
 * @since 3.26
 */
@Singleton
@Named(Maven2Format.NAME)
public class MavenSearchDocumentProducer
    extends DefaultSearchDocumentProducer
{
  @Inject
  public MavenSearchDocumentProducer(final Set<SearchDocumentExtension> documentExtensions) {
    super(documentExtensions);
  }

  @Override
  protected boolean isPrerelease(final FluentComponent component) {
    String baseVersion = (String) component.attributes().child(Maven2Format.NAME).get(P_BASE_VERSION);
    if (baseVersion == null) {
      return false;
    }
    return baseVersion.endsWith(SNAPSHOT_VERSION_SUFFIX);
  }
}
