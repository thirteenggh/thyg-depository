package org.sonatype.nexus.datastore;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.datastore.api.DataStoreConfiguration;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static com.google.common.collect.Streams.stream;
import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * Manages {@link DataStoreConfiguration}s supplied by one or more sources.
 *
 * @since 3.19
 */
@Named
@Singleton
public class DataStoreConfigurationManager
    extends ComponentSupport
{
  private final Map<String, DataStoreConfigurationSource> configurationSources;

  @Inject
  public DataStoreConfigurationManager(final Map<String, DataStoreConfigurationSource> configurationSources) {
    this.configurationSources = checkNotNull(configurationSources);
  }

  /**
   * Loads {@link DataStoreConfiguration}s from all enabled sources.
   */
  public Iterable<DataStoreConfiguration> load() {
    Set<String> configuredStores = new TreeSet<>(CASE_INSENSITIVE_ORDER);

    // only attempt to load a named store once from the first store that has it
    // (if the first attempt fails then that store is considered not available)
    return configurationSources.values().stream()
        .filter(DataStoreConfigurationSource::isEnabled)
        .flatMap(source -> stream(source.browseStoreNames())
            .filter(configuredStores::add)
            .map(configLoader(source)))
        .filter(Objects::nonNull)
        .collect(toImmutableList());
  }

  /**
   * Saves the given configuration back to the source that originally loaded it.
   */
  public void save(final DataStoreConfiguration configuration) {
    findModifiableSource(configuration).save(configuration);
  }

  /**
   * Deletes the given configuration from the source that originally loaded it.
   */
  public void delete(final DataStoreConfiguration configuration) {
    findModifiableSource(configuration).delete(configuration);
  }

  /**
   * @return loader that returns {@code null} if a configuration cannot be loaded
   */
  private Function<String, DataStoreConfiguration> configLoader(final DataStoreConfigurationSource source) {
    return storeName -> {
      try {
        return source.load(storeName);
      }
      catch (RuntimeException e) {
        log.warn("Problem reading configuration of data store {} from {}", storeName, source, e);
        return null;
      }
    };
  }

  /**
   * Attempts to find the modifiable source that originally loaded the given configuration.
   */
  private DataStoreConfigurationSource findModifiableSource(final DataStoreConfiguration configuration) {
    DataStoreConfigurationSource source = configurationSources.get(configuration.getSource());

    checkArgument(source != null, "%s refers to a missing source", configuration);
    checkArgument(source.isModifiable(), "%s is from a read-only source", configuration);

    return source;
  }
}
