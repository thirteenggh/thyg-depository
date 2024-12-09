package org.sonatype.nexus.repository.maven.rest;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.maven.api.MavenAttributes;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"format", "type"})
public class MavenHostedRepositoryApiRequest
    extends HostedRepositoryApiRequest
{
  @NotNull
  protected final MavenAttributes maven;

  @JsonCreator
  public MavenHostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("maven") final MavenAttributes maven
  )
  {
    super(name, Maven2Format.NAME, online, storage, cleanup);
    this.maven = maven;
  }

  public MavenAttributes getMaven() {
    return maven;
  }
}
