package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.HostedType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"type"})
public class HostedRepositoryApiRequest
    extends AbstractRepositoryApiRequest
{
  @NotNull
  @Valid
  private final HostedStorageAttributes storage;

  @Valid
  private final CleanupPolicyAttributes cleanup;

  @JsonCreator
  public HostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup)
  {
    super(name, format, HostedType.NAME, online);
    this.storage = storage;
    this.cleanup = cleanup;
  }

  public HostedStorageAttributes getStorage() {
    return storage;
  }

  public CleanupPolicyAttributes getCleanup() {
    return cleanup;
  }
}
