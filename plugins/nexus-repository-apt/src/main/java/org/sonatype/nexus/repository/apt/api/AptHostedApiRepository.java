package org.sonatype.nexus.repository.apt.api;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.apt.internal.AptFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HostedStorageAttributes;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiHostedRepository;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST API model representing an Apt repository.
 * 
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class AptHostedApiRepository
    extends SimpleApiHostedRepository
{
  @NotNull
  protected final AptHostedRepositoriesAttributes apt;

  @NotNull
  protected final AptSigningRepositoriesAttributes aptSigning;

  @JsonCreator
  public AptHostedApiRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final HostedStorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("apt") final AptHostedRepositoriesAttributes apt,
      @JsonProperty("aptSigning") final AptSigningRepositoriesAttributes aptSigning)
  {
    super(name, AptFormat.NAME, url, online, storage, cleanup);
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
