package org.sonatype.nexus.repository.apt.rest;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.HostedRepositoryApiRequestToConfigurationConverter;

/**
 * @since 3.20
 */
@Named
public class AptHostedRepositoryApiRequestToConfigurationConverter
    extends HostedRepositoryApiRequestToConfigurationConverter<AptHostedRepositoryApiRequest>
{
  @Override
  public Configuration convert(final AptHostedRepositoryApiRequest request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("apt").set("distribution", request.getApt().getDistribution());
    configuration.attributes("aptSigning").set("keypair", request.getAptSigning().getKeypair());
    configuration.attributes("aptSigning").set("passphrase", request.getAptSigning().getPassphrase());
    return configuration;
  }
}
