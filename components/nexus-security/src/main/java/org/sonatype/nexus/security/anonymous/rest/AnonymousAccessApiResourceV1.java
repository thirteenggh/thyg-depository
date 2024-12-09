package org.sonatype.nexus.security.anonymous.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.security.anonymous.AnonymousManager;
import org.sonatype.nexus.security.internal.rest.SecurityApiResourceV1;

import org.apache.shiro.mgt.RealmSecurityManager;

import static org.sonatype.nexus.security.anonymous.rest.AnonymousAccessApiResourceV1.RESOURCE_URI;

/**
 * @since 3.26
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class AnonymousAccessApiResourceV1
    extends AnonymousAccessApiResource
{
  static final String RESOURCE_URI = SecurityApiResourceV1.V1_RESOURCE_URI + "anonymous";

  @Inject
  public AnonymousAccessApiResourceV1(
      final AnonymousManager anonymousManager,
      final RealmSecurityManager realmSecurityManager)
  {
    super(anonymousManager, realmSecurityManager);
  }
}
