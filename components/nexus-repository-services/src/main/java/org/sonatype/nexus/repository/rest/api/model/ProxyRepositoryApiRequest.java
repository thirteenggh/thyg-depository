package org.sonatype.nexus.repository.rest.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.sonatype.nexus.repository.types.ProxyType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @since 3.20
 */
public class ProxyRepositoryApiRequest
    extends AbstractRepositoryApiRequest
{
  @NotNull
  @Valid
  private final StorageAttributes storage;

  @Valid
  private final CleanupPolicyAttributes cleanup;

  @NotNull
  @Valid
  private final ProxyAttributes proxy;

  @NotNull
  @Valid
  private final NegativeCacheAttributes negativeCache;

  @NotNull
  @Valid
  private final HttpClientAttributes httpClient;

  private final String routingRule;

  @SuppressWarnings("squid:S00107") // suppress constructor parameter count
  @JsonCreator
  public ProxyRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("format") final String format,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache") final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRule") final String routingRule)
  {
    super(name, format, ProxyType.NAME, online);
    this.storage = storage;
    this.cleanup = cleanup;
    this.proxy = proxy;
    this.negativeCache = negativeCache;
    this.httpClient = httpClient;
    this.routingRule = routingRule;
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

  public String getRoutingRule() {
    return routingRule;
  }
}
