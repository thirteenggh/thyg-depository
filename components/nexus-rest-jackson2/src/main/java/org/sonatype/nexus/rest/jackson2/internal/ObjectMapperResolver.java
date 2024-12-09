package org.sonatype.nexus.rest.jackson2.internal;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Jackson {@link ObjectMapper} resolver.
 *
 * This will use the mapper bound to the name "siesta".
 *
 * @since 3.0
 * @see ObjectMapperProvider
 */
@Named
@Singleton
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class ObjectMapperResolver
  extends ComponentSupport
  implements ContextResolver<ObjectMapper>, Component
{
  private final javax.inject.Provider<ObjectMapper> mapperProvider;

  @Inject
  public ObjectMapperResolver(@Named("siesta") final javax.inject.Provider<ObjectMapper> mapperProvider) {
    this.mapperProvider = checkNotNull(mapperProvider);
  }

  @Override
  public ObjectMapper getContext(final Class<?> type) {
    return mapperProvider.get();
  }
}
