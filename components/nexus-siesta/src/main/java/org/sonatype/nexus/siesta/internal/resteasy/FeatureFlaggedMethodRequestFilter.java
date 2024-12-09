package org.sonatype.nexus.siesta.internal.resteasy;

import java.lang.reflect.Method;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.FeatureFlaggedMethod;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static org.sonatype.nexus.common.property.SystemPropertiesHelper.getBoolean;

@FeatureFlaggedMethod
public class FeatureFlaggedMethodRequestFilter
    extends ComponentSupport
    implements ContainerRequestFilter
{
  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {
    Method method = resourceInfo.getResourceMethod();
    FeatureFlaggedMethod annotation = method.getAnnotation(FeatureFlaggedMethod.class);
    String featureFlagName = annotation.name();
    if (method != null) {
      boolean enabled = getBoolean(featureFlagName, annotation.enabledByDefault());
      if (!enabled) {
        log.debug("Filtering method {} flagged by feature flag {}", method.getName(), featureFlagName);
        requestContext.abortWith(Response.status(FORBIDDEN).entity("feature is disabled").build());
      }
    }
  }
}
