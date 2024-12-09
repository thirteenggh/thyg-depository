package org.sonatype.nexus.internal.rest;

import java.util.SortedMap;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.Resource;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.20
 */
@Named
@Singleton
@Path(HealthCheckResource.RESOURCE_URI)
@Produces(APPLICATION_JSON)
public class HealthCheckResource
    extends ComponentSupport
    implements Resource
{
  public static final String RESOURCE_URI = "/internal/ui/status-check";

  private HealthCheckRegistry registry;

  @Inject
  public HealthCheckResource(HealthCheckRegistry registry) {
    this.registry = checkNotNull(registry);
  }

  @GET
  @RequiresAuthentication
  @RequiresPermissions("nexus:metrics:read")
  public SortedMap<String, Result> getSystemStatusChecks() {
    return registry.runHealthChecks();
  }
}
