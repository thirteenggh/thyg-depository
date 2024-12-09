package org.sonatype.nexus.security.realm.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.realm.RealmManager;

import io.swagger.annotations.Api;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RealmApiResourceBeta.RESOURCE_URL)
@Deprecated
public class RealmApiResourceBeta
    extends RealmApiResource
{
  static final String RESOURCE_URL = APIConstants.BETA_API_PREFIX + "/security/realms";

  @Inject
  public RealmApiResourceBeta(final RealmManager realmManager) {
    super(realmManager);
  }
}
