package org.sonatype.nexus.swagger.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.rest.Component;

/**
 * Siesta-managed {@link io.swagger.jaxrs.listing.SwaggerSerializers}.
 * 
 * @since 3.3
 */
@Named
@Singleton
public class SwaggerSerializers
    extends io.swagger.jaxrs.listing.SwaggerSerializers
    implements Component
{
  public SwaggerSerializers() { // NOSONAR
    setPrettyPrint(true);
  }
}
