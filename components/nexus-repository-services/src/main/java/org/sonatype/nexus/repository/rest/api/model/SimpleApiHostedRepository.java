package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.HostedType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * API Hosted Repository for simple formats which do not have custom attributes for hosted repositories.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class SimpleApiHostedRepository
    extends AbstractApiRepository
{
  @NotNull
  protected final HostedStorageAttributes storage;

  protected final CleanupPolicyAttributes cleanup;

  @JsonCreator
  public SimpleApiHostedRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup)
  {
    super(name, format, HostedType.NAME, url, online);
    this.storage = storage;
    this.cleanup = cleanup;
  }

  public CleanupPolicyAttributes getCleanup() {
    return cleanup;
  }

  public HostedStorageAttributes getStorage() {
    return storage;
  }
}
