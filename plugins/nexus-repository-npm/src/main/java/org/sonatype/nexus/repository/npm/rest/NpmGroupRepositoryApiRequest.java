package org.sonatype.nexus.repository.npm.rest;

import org.sonatype.nexus.repository.npm.internal.NpmFormat;
import org.sonatype.nexus.repository.rest.api.model.GroupDeployAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupDeployRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.22
 */
@JsonIgnoreProperties({"format", "type"})
public class NpmGroupRepositoryApiRequest
    extends GroupDeployRepositoryApiRequest
{
  @JsonCreator
  public NpmGroupRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("group") final GroupDeployAttributes group)
  {
    super(name, NpmFormat.NAME, online, storage, group);
  }
}
