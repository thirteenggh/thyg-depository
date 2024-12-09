package org.sonatype.nexus.rest.jackson2.internal;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.sonatype.goodies.common.ComponentSupport;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Jackson {@link ObjectMapper} provider for use with Siesta.
 *
 * @since 3.0
 */
@Named("siesta")
@Singleton
public class ObjectMapperProvider
    extends ComponentSupport
    implements Provider<ObjectMapper>
{
  private final ObjectMapper mapper;

  public ObjectMapperProvider() {
    this.mapper = new ObjectMapper()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Override
  public ObjectMapper get() {
    return mapper;
  }
}
