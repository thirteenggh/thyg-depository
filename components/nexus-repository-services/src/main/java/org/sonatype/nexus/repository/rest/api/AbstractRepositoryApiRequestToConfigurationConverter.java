package org.sonatype.nexus.repository.rest.api;

import javax.inject.Inject;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.config.ConfigurationStore;
import org.sonatype.nexus.repository.rest.api.model.AbstractRepositoryApiRequest;

/**
 * @since 3.20
 */
public abstract class AbstractRepositoryApiRequestToConfigurationConverter<T extends AbstractRepositoryApiRequest>
{
  @Inject
  protected ConfigurationStore configurationStore;

  public Configuration convert(final T request) {
    Configuration configuration = configurationStore.newConfiguration();
    configuration.setRepositoryName(request.getName());
    configuration.setRecipeName(String.join("-", request.getFormat(), request.getType()));
    configuration.setOnline(request.getOnline());
    return configuration;
  }
}
