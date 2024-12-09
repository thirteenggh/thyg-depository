package org.sonatype.nexus.repository.maven.api;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiHostedRepository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST API model for a maven hosted repository.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class MavenHostedApiRepository
    extends SimpleApiHostedRepository
{
  @NotNull
  protected final MavenAttributes maven;

  @JsonCreator
  public MavenHostedApiRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("maven") final MavenAttributes maven)
  {
    super(name, Maven2Format.NAME, url, online, storage, cleanup);
    this.maven = maven;
  }

  public MavenAttributes getMaven() {
    return maven;
  }
}
