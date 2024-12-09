package org.sonatype.nexus.repository.r.api;

import org.sonatype.nexus.repository.r.internal.RFormat;
import org.sonatype.nexus.repository.rest.api.model.GroupAttributes;
import org.sonatype.nexus.repository.rest.api.model.GroupRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.28
 */
@JsonIgnoreProperties({"format", "type"})
public class RGroupRepositoryApiRequest
    extends GroupRepositoryApiRequest
{
  @JsonCreator
  public RGroupRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("group") final GroupAttributes group)
  {
    super(name, RFormat.NAME, online, storage, group);
  }
}
