package org.sonatype.nexus.repository.npm.rest;

import org.sonatype.nexus.repository.npm.internal.NpmFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.22
 */
@JsonIgnoreProperties({"format", "type"})
public class NpmHostedRepositoryApiRequest
    extends HostedRepositoryApiRequest
{
  @JsonCreator
  public NpmHostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup
  )
  {
    super(name, NpmFormat.NAME, online, storage, cleanup);
  }
}
