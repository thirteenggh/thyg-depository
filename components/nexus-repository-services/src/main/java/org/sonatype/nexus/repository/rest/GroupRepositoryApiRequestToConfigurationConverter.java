package org.sonatype.nexus.repository.rest;

import javax.inject.Named;

import org.sonatype.nexus.repository.config.Configuration;
import org.sonatype.nexus.repository.rest.api.AbstractRepositoryApiRequestToConfigurationConverter;
import org.sonatype.nexus.repository.rest.api.model.GroupAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupDeployAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupRepositoryApiRequest;

import static org.sonatype.nexus.repository.config.ConfigurationConstants.GROUP_WRITE_MEMBER;

/**
 * @since 3.20
 */
@Named
public class GroupRepositoryApiRequestToConfigurationConverter<T extends GroupRepositoryApiRequest>
    extends AbstractRepositoryApiRequestToConfigurationConverter<T>
{
  public Configuration convert(final T request) {
    Configuration configuration = super.convert(request);
    configuration.attributes("storage").set("blobStoreName", request.getStorage().getBlobStoreName());
    configuration.attributes("storage")
        .set("strictContentTypeValidation", request.getStorage().getStrictContentTypeValidation());
    configuration.attributes("group").set("memberNames", request.getGroup().getMemberNames());

    GroupAttributes group = request.getGroup();
    if (group instanceof GroupDeployAttributes) {
      GroupDeployAttributes groupDeployAttributes = (GroupDeployAttributes) group;
      String writableMember = groupDeployAttributes.getWritableMember();
      if (writableMember != null && !writableMember.isEmpty()) {
        configuration.attributes("group").set(GROUP_WRITE_MEMBER, writableMember);
      }
    }
    return configuration;
  }
}
