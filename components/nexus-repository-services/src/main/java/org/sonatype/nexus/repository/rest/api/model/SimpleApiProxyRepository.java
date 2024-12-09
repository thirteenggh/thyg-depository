package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.ProxyType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * API Proxy Repository for simple formats which do not have custom attributes for proxies.
 *
 * @since 3.20
 */
@JsonIgnoreProperties(value = {"format", "type", "url"}, allowGetters = true)
public class SimpleApiProxyRepository
    extends AbstractApiRepository
{
  @NotNull
  protected final StorageAttributes storage;

  protected final CleanupPolicyAttributes cleanup;

  @NotNull
  protected final ProxyAttributes proxy;

  @NotNull
  protected final NegativeCacheAttributes negativeCache;

  @NotNull
  protected final HttpClientAttributes httpClient;

  @ApiModelProperty(value = "The name of the routing rule assigned to this repository")
  protected final String routingRuleName;

  @JsonCreator
  public SimpleApiProxyRepository(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("url") final String url,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRuleName") final String routingRuleName)
  {
    super(name, format, ProxyType.NAME, url, online);
    this.storage = storage;
    this.cleanup = cleanup;
    this.proxy = proxy;
    this.negativeCache = negativeCache;
    this.httpClient = httpClient;
    this.routingRuleName = routingRuleName;
  }

  public StorageAttributes getStorage() {
    return storage;
  }

  public CleanupPolicyAttributes getCleanup() {
    return cleanup;
  }

  public ProxyAttributes getProxy() {
    return proxy;
  }

  public NegativeCacheAttributes getNegativeCache() {
    return negativeCache;
  }

  public HttpClientAttributes getHttpClient() {
    return httpClient;
  }

  public String getRoutingRuleName() {
    return routingRuleName;
  }
}
