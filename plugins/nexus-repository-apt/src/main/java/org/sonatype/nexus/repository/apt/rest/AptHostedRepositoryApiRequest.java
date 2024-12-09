package org.sonatype.nexus.repository.apt.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.apt.api.AptHostedRepositoriesAttributes;
import org.sonatype.nexus.repository.apt.internal.AptFormat;
import org.sonatype.nexus.repository.rest.api.model.HostedRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"format", "type"})
public class AptHostedRepositoryApiRequest
    extends HostedRepositoryApiRequest
{
  @NotNull
  @Valid
  protected final AptHostedRepositoriesAttributes apt;

  @NotNull
  @Valid
  protected final AptSigningRepositoriesAttributes aptSigning;

  @JsonCreator
  public AptHostedRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("apt") final AptHostedRepositoriesAttributes apt,
      @JsonProperty("aptSigning") final AptSigningRepositoriesAttributes aptSigning)
  {
    super(name, AptFormat.NAME, online, storage, cleanup);
    this.apt = apt;
    this.aptSigning = aptSigning;
  }

  public AptHostedRepositoriesAttributes getApt() {
    return apt;
  }

  public AptSigningRepositoriesAttributes getAptSigning() {
    return aptSigning;
  }
}
