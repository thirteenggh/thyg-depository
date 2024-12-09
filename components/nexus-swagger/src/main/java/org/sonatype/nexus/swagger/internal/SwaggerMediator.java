package org.sonatype.nexus.swagger.internal;

import javax.inject.Named;

import org.sonatype.nexus.rest.Resource;

import org.eclipse.sisu.BeanEntry;
import org.eclipse.sisu.Mediator;

/**
 * Mediator between Sisu-managed JAX-RS resources and Swagger.
 * 
 * @since 3.3
 */
@Named
public class SwaggerMediator
    implements Mediator<Named, Resource, SwaggerModel>
{
  @Override
  public void add(final BeanEntry<Named, Resource> resource, final SwaggerModel swagger) {
    swagger.scan(resource.getImplementationClass());
  }

  @Override
  public void remove(final BeanEntry<Named, Resource> resource, final SwaggerModel swagger) {
    // nothing to do
  }
}
