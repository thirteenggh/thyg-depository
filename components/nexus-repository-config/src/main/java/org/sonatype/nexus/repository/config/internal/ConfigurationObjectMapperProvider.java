package org.sonatype.nexus.repository.config.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.repository.config.ConfigurationObjectMapperCustomizer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Provides {@link ObjectMapper} for repository configuration.
 *
 * @since 3.0
 */
@Named(ConfigurationObjectMapperProvider.NAME)
@Singleton
public class ConfigurationObjectMapperProvider
    extends ComponentSupport
    implements Provider<ObjectMapper>
{
  public static final String NAME = "repository-configuration";

  private final Map<String, ConfigurationObjectMapperCustomizer> customizers;

  @Inject
  public ConfigurationObjectMapperProvider(final Map<String, ConfigurationObjectMapperCustomizer> customizers)
  {
    this.customizers = checkNotNull(customizers);
  }

  @Override
  public ObjectMapper get() {
    ObjectMapper mapper = new ObjectMapper().enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    for (ConfigurationObjectMapperCustomizer customizer : customizers.values()) {
      customizer.customize(mapper);
    }
    // TODO: ISO-8601, joda
    // TODO: null handling
    return mapper;
  }
}
