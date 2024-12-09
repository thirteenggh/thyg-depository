package org.sonatype.nexus.security.realm.api;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.realm.RealmManager;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RealmApiResourceV1.RESOURCE_URL)
public class RealmApiResourceV1
    extends RealmApiResource
{
  static final String RESOURCE_URL = APIConstants.V1_API_PREFIX + "/security/realms";

  @Inject
  public RealmApiResourceV1(final RealmManager realmManager) {
    super(realmManager);
  }
}
