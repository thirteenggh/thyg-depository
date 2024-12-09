package org.sonatype.nexus.coreui;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.sonatype.goodies.common.ComponentSupport;
import org.sonatype.nexus.rest.Resource;
import org.sonatype.nexus.security.realm.RealmManager;
import org.sonatype.nexus.security.realm.SecurityRealm;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @since 3.19
 */
@Named
@Singleton
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path(RealmSettingsResource.RESOURCE_PATH)
public class RealmSettingsResource
    extends ComponentSupport
    implements Resource
{
  static final String RESOURCE_PATH = "internal/ui/realms";

  private final RealmManager realmManager;

  @Inject
  public RealmSettingsResource(final RealmManager realmManager) {
    this.realmManager = checkNotNull(realmManager);
  }

  @GET
  @Path("/types")
  @RequiresPermissions("nexus:settings:read")
  public List<SecurityRealm> readRealmTypes() {
    return realmManager.getAvailableRealms();
  }
}
