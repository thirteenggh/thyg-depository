package org.sonatype.nexus.repository.maven.rest;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.maven.api.MavenAttributes;
import org.sonatype.nexus.repository.maven.internal.Maven2Format;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"format", "type"})
public class MavenProxyRepositoryApiRequest
    extends ProxyRepositoryApiRequest
{
  @NotNull
  protected final MavenAttributes maven;

  @JsonCreator
  @SuppressWarnings("squid:S00107") // suppress constructor parameter count
  public MavenProxyRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRule") final String routingRule,
      @JsonProperty("maven") final MavenAttributes maven)
  {
    super(name, Maven2Format.NAME, online, storage, cleanup, proxy, negativeCache, httpClient, routingRule);
    this.maven = maven;
  }

  public MavenAttributes getMaven() {
    return maven;
  }
}
