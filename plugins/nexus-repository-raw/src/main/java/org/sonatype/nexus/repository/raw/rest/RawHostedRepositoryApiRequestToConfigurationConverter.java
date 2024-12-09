package org.sonatype.nexus.repository.raw.rest;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.HostedRepositoryApiRequestToConfigurationConverter;

import static org.sonatype.nexus.repository.raw.rest.RawAttributes.CONTENT_DISPOSITION;

/**
 * @since 3.25
 */
@Named
public class RawHostedRepositoryApiRequestToConfigurationConverter
    extends HostedRepositoryApiRequestToConfigurationConverter<RawHostedRepositoryApiRequest>
{
  @Override
  public Configuration convert(final RawHostedRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("raw").set(CONTENT_DISPOSITION, request.getRaw().getContentDisposition().name());
    return configuration;
  }
}
