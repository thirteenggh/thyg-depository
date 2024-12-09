package org.sonatype.nexus.internal.email.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Path;

import org.sonatype.nexus.email.EmailManager;

import static org.sonatype.nexus.internal.email.rest.EmailConfigurationApiResourceV1.RESOURCE_URI;
import static org.sonatype.nexus.rest.APIConstants.V1_API_PREFIX;

/**
 * v1 endpoint for email configuration REST API
 *
 * @since 3.25
 */
@Named
@Singleton
@Path(RESOURCE_URI)
public class EmailConfigurationApiResourceV1
  extends EmailConfigurationApiResource
{
  static final String RESOURCE_URI = V1_API_PREFIX + "/email";

  @Inject
  public EmailConfigurationApiResourceV1(final EmailManager emailManager) {
    super(emailManager);
  }
}
