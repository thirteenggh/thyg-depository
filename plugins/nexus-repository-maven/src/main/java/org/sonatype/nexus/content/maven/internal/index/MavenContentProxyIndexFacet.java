package org.sonatype.nexus.content.maven.internal.index;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationFacet;
import org.sonatype.nexus.repository.maven.MavenIndexFacet;
import org.sonatype.nexus.repository.maven.internal.MavenIndexPublisher;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategy;
import org.sonatype.nexus.repository.maven.internal.filter.DuplicateDetectionStrategyProvider;
import org.sonatype.nexus.repository.types.ProxyType;

import com.google.common.annotations.VisibleForTesting;
import org.apache.maven.index.reader.Record;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Proxy implementation of {@link MavenIndexFacet}.
 *
 * @since 3.26
 */
@Named
public class MavenContentProxyIndexFacet
    extends MavenContentIndexFacetSupport
{
  static final String CONFIG_KEY = "maven-indexer";

  @VisibleForTesting
  static class Config
  {
    @NotNull(groups = ProxyType.ValidationGroup.class)
    public Boolean cacheFallback = Boolean.FALSE;

    @Override
    public String toString() {
      return getClass().getSimpleName() + "{" +
          "cacheFallback=" + cacheFallback +
          '}';
    }
  }

  private Config config;

  private final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider;

  @Inject
  public MavenContentProxyIndexFacet(
      final MavenIndexPublisher mavenIndexPublisher,
      final DuplicateDetectionStrategyProvider duplicateDetectionStrategyProvider)
  {
    super(mavenIndexPublisher);
    this.duplicateDetectionStrategyProvider = checkNotNull(duplicateDetectionStrategyProvider);
  }

  @Override
  protected void doValidate(final Configuration configuration) {
    facet(ConfigurationFacet.class).validateSection(configuration, CONFIG_KEY, MavenContentProxyIndexFacet.Config.class,
        Default.class, getRepository().getType().getValidationGroup()
    );
  }

  @Override
  protected void doConfigure(final Configuration configuration) {
    config = facet(ConfigurationFacet.class)
        .readSection(configuration, CONFIG_KEY, MavenContentProxyIndexFacet.Config.class);
    log.debug("Config: {}", config);
  }

  @Override
  public void publishIndex() throws IOException {
    log.debug("Fetching maven index properties from remote");
    try (DuplicateDetectionStrategy<Record> strategy = duplicateDetectionStrategyProvider.get()) {
      mavenIndexPublisher.publishProxyIndex(getRepository(), config.cacheFallback, strategy );
    }
  }
}
