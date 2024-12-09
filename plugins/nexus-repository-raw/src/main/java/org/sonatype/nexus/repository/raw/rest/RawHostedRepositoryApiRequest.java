package org.sonatype.nexus.repository.raw.rest;

import org.sonatype.nexus.repository.raw.internal.RawFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.sonatype.nexus.content.raw.internal.recipe.ContentDisposition.ATTACHMENT;

/**
 * @since 3.24
 */
@JsonIgnoreProperties({"format", "type"})
public class RawHostedRepositoryApiRequest
    extends HostedRepositoryApiRequest
{
  private final RawAttributes raw;

  @JsonCreator
  public RawHostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("raw") final RawAttributes raw)
  {
    super(name, RawFormat.NAME, online, storage, cleanup);
    this.raw = raw != null ? raw : new RawAttributes(ATTACHMENT);
  }

  public RawAttributes getRaw() {
    return raw;
  }
}
