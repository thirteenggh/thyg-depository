package org.sonatype.nexus.security.anonymous.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.rest.APIConstants;
import org.sonatype.nexus.security.anonymous.AnonymousManager;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceBeta;

import io.swagger.annotations.Api;
import org.apache.shiro.mgt.RealmSecurityManager;

import static org.sonatype.nexus.security.anonymous.rest.AnonymousAccessApiResourceBeta.RESOURCE_URI;

/**
 * @since 3.26
 * @deprecated beta prefix is being phased out, prefer starting new APIs with {@link APIConstants#V1_API_PREFIX} instead
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class AnonymousAccessApiResourceBeta
    extends AnonymousAccessApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceBeta.BETA_RESOURCE_URI + "anonymous";

  @Inject
  public AnonymousAccessApiResourceBeta(
      final AnonymousManager anonymousManager,
      final RealmSecurityManager realmSecurityManager)
  {
    super(anonymousManager, realmSecurityManager);
  }
}
