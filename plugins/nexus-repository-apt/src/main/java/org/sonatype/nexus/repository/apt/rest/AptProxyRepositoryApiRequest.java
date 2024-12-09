package org.sonatype.nexus.repository.apt.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.apt.internal.AptFormat;
import org.sonatype.nexus.repository.rest.api.model.ProxyRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
@JsonIgnoreProperties({"format", "type"})
public class AptProxyRepositoryApiRequest
    extends ProxyRepositoryApiRequest
{
  @NotNull
  @Valid
  protected final AptProxyRepositoriesAttributes apt;

  @SuppressWarnings("squid:S00107") // suppress constructor parameter count
  @JsonCreator
  public AptProxyRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("apt") final AptProxyRepositoriesAttributes apt,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRule") final String routingRule)
  {
    super(name, AptFormat.NAME, online, storage, cleanup, proxy, negativeCache, httpClient, routingRule);
    this.apt = apt;
  }

  public AptProxyRepositoriesAttributes getApt() {
    return apt;
  }
}
