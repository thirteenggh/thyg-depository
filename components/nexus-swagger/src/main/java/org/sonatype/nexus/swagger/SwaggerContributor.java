package org.sonatype.nexus.swagger;

import io.swagger.models.Swagger;

/**
 * Listener providing a hook for customizing the {@link Swagger} model.
 *
 * @since 3.7
 */
public interface SwaggerContributor
{
  /**
   * Call after JAX-RS resource has been scanned.
   * @param swagger the swagger definition
   */
  void contribute(Swagger swagger);
}
