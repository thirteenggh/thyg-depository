package org.sonatype.nexus.repository.config.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.sonatype.nexus.common.collect.AttributesMap;
import org.sonatype.nexus.repository.FacetSupport;
import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationFacet;
import org.sonatype.nexus.repository.config.ConfigurationStore;
import org.sonatype.nexus.validation.ConstraintViolations;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Default {@link ConfigurationFacet} implementation.
 *
 * @since 3.0
 */
@Named
public class ConfigurationFacetImpl
    extends FacetSupport
    implements ConfigurationFacet
{
  private final ConfigurationStore store;

  private final ObjectMapper objectMapper;

  private final Provider<Validator> validatorProvider;

  @Inject
  public ConfigurationFacetImpl(final ConfigurationStore store,
                                @Named(ConfigurationObjectMapperProvider.NAME) final ObjectMapper objectMapper,
                                final Provider<Validator> validatorProvider)
  {
    this.store = checkNotNull(store);
    this.objectMapper = checkNotNull(objectMapper);
    this.validatorProvider = checkNotNull(validatorProvider);
  }

  @Override
  public void save() throws Exception {
    store.update(getRepository().getConfiguration());
    log.debug("Saved");
  }

  @Override
  public <T> T convert(final Object value, final Class<T> type) {
    checkNotNull(value);
    checkNotNull(type);
    log.trace("Converting value: {} to type: {}", value, type);
    return objectMapper.convertValue(value, type);
  }

  @Override
  public <T> T readSection(final Configuration configuration, final String section, final Class<T> type) {
    checkNotNull(configuration);
    checkNotNull(section);
    log.trace("Reading section: {}", section);
    AttributesMap attributes = configuration.attributes(section);
    return convert(attributes.backing(), type);
  }

  @Override
  public void validate(final Object value, final Class<?>... groups) {
    checkNotNull(value);
    checkNotNull(groups);

    if (log.isTraceEnabled()) {
      log.trace("Validating: {} in groups: {}", value, Arrays.asList(groups));
    }

    Validator validator = validatorProvider.get();
    Set<ConstraintViolation<Object>> violations = validator.validate(value, groups);
    ConstraintViolations.maybePropagate(violations, log);
  }

  /**
   * Wrap attribute section value to allow configuration hierarchy to be encoded into property path.
   */
  private static class SectionWrapper
  {
    @SuppressWarnings("unused")
    @Valid
    private Map<String,Object> attributes;

    public SectionWrapper(final String name, final Object value) {
      this.attributes = Collections.singletonMap(name, value);
    }
  }

  @Override
  public <T> T validateSection(final Configuration configuration,
                               final String section,
                               final Class<T> type,
                               final Class<?>... groups)
  {
    T value = readSection(configuration, section, type);
    SectionWrapper wrapper = new SectionWrapper(section, value);
    validate(wrapper, groups);
    return value;
  }
}
