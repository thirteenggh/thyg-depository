package org.sonatype.nexus.repository.apt.api;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.apt.internal.AptFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.SimpleApiProxyRepository;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST API model representing an Apt proxy repository.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class AptProxyApiRepository
    extends SimpleApiProxyRepository
{
  @NotNull
  protected final AptProxyRepositoriesAttributes apt;

  @JsonCreator
  public AptProxyApiRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("apt") final AptProxyRepositoriesAttributes apt,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRuleName") final String routingRuleName)
  {
    super(name, AptFormat.NAME, url, online, storage, cleanup, proxy, negativeCache, httpClient, routingRuleName);
    this.apt = apt;
  }

  public AptProxyRepositoriesAttributes getApt() {
    return apt;
  }
}
