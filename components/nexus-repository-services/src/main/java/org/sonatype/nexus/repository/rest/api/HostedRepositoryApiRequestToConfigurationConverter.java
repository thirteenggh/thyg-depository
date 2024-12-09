package org.sonatype.nexus.repository.rest.api;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;

import com.google.common.collect.Sets;

import static org.sonatype.nexus.repository.config.ConfigurationConstants.BLOB_STORE_NAME;
import static org.sonatype.nexus.repository.config.ConfigurationConstants.STORAGE;
import static org.sonatype.nexus.repository.config.ConfigurationConstants.STRICT_CONTENT_TYPE_VALIDATION;
import static org.sonatype.nexus.repository.config.ConfigurationConstants.WRITE_POLICY;
import static org.sonatype.nexus.repository.manager.internal.RepositoryManagerImpl.CLEANUP_ATTRIBUTES_KEY;
import static org.sonatype.nexus.repository.manager.internal.RepositoryManagerImpl.CLEANUP_NAME_KEY;

/**
 * @since 3.20
 */
@Named
public class HostedRepositoryApiRequestToConfigurationConverter<T extends HostedRepositoryApiRequest>
    extends AbstractRepositoryApiRequestToConfigurationConverter<T>
{
  public Configuration convert(final T request) {
    Configuration configuration = super.convert(request);
    configuration.attributes(STORAGE).set(BLOB_STORE_NAME, request.getStorage().getBlobStoreName());
    configuration.attributes(STORAGE)
        .set(STRICT_CONTENT_TYPE_VALIDATION, request.getStorage().getStrictContentTypeValidation());
    configuration.attributes(STORAGE).set(WRITE_POLICY, request.getStorage().getWritePolicy());
    if (request.getCleanup() != null) {
      configuration.attributes(CLEANUP_ATTRIBUTES_KEY)
          .set(CLEANUP_NAME_KEY, Sets.newHashSet(request.getCleanup().getPolicyNames()));
    }
    return configuration;
  }
}
