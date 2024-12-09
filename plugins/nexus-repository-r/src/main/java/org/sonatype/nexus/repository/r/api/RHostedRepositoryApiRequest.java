package org.sonatype.nexus.repository.r.api;

import org.sonatype.nexus.repository.r.internal.RFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.28
 */
@JsonIgnoreProperties({"format", "type"})
public class RHostedRepositoryApiRequest
    extends HostedRepositoryApiRequest
{
  @JsonCreator
  public RHostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup)
  {
    super(name, RFormat.NAME, online, storage, cleanup);
  }
}
