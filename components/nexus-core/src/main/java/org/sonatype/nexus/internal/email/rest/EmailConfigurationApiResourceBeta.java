package org.sonatype.nexus.internal.email.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.email.EmailManager;

import io.swagger.annotations.Api;

import static org.sonatype.nexus.internal.email.rest.EmailConfigurationApiResourceBeta.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.BETA_API_PREFIX;

/**
 * beta endpoint for email configuration REST API
 *
 * @since 3.25
 * @deprecated moving to {@link EmailConfigurationApiResourceV1}
 */
@Api(hidden = true)
@Named
@Singleton
@Path(RESOURCE_URI)
@Deprecated
public class EmailConfigurationApiResourceBeta
  extends EmailConfigurationApiResource
{
  static final String RESOURCE_URI = BETA_API_PREFIX + "/email";

  @Inject
  public EmailConfigurationApiResourceBeta(final EmailManager emailManager) {
    super(emailManager);
  }
}
