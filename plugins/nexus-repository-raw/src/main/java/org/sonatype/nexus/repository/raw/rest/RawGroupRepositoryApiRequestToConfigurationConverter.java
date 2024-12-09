package org.sonatype.nexus.repository.raw.rest;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.GroupRepositoryApiRequestToConfigurationConverter;

import static org.sonatype.nexus.repository.raw.rest.RawAttributes.CONTENT_DISPOSITION;

/**
 * @since 3.25
 */
@Named
public class RawGroupRepositoryApiRequestToConfigurationConverter
    extends GroupRepositoryApiRequestToConfigurationConverter<RawGroupRepositoryApiRequest>
{
  @Override
  public Configuration convert(final RawGroupRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("raw").set(CONTENT_DISPOSITION, request.getRaw().getContentDisposition().name());
    return configuration;
  }
}
