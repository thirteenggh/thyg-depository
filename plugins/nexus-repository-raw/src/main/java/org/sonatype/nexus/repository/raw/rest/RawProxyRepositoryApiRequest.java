package org.sonatype.nexus.repository.raw.rest;

import org.sonatype.nexus.repository.raw.internal.RawFormat;
import org.sonatype.nexus.repository.rest.api.model.CleanupPolicyAttributes;
import org.sonatype.nexus.repository.rest.api.model.HttpClientAttributes;
import org.sonatype.nexus.repository.rest.api.model.NegativeCacheAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyAttributes;
import org.sonatype.nexus.repository.rest.api.model.ProxyRepositoryApiRequest;
import org.sonatype.nexus.repository.rest.api.model.StorageAttributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import static org.sonatype.nexus.content.raw.internal.recipe.ContentDisposition.ATTACHMENT;

/**
 * @since 3.24
 */
@JsonIgnoreProperties({"format", "type"})
public class RawProxyRepositoryApiRequest
    extends ProxyRepositoryApiRequest
{
  private final RawAttributes raw;

  @JsonCreator
  @SuppressWarnings("squid:S00107") // suppress constructor parameter count
  public RawProxyRepositoryApiRequest(
      @JsonProperty("name") final String name,
      @JsonProperty("online") final Boolean online,
      @JsonProperty("storage") final StorageAttributes storage,
      @JsonProperty("cleanup") final CleanupPolicyAttributes cleanup,
      @JsonProperty("proxy") final ProxyAttributes proxy,
      @JsonProperty("negativeCache")  final NegativeCacheAttributes negativeCache,
      @JsonProperty("httpClient") final HttpClientAttributes httpClient,
      @JsonProperty("routingRule") final String routingRule,
      @JsonProperty("raw") final RawAttributes raw)
  {
    super(name, RawFormat.NAME, online, storage, cleanup, proxy, negativeCache, httpClient, routingRule);
    this.raw = raw != null ? raw : new RawAttributes(ATTACHMENT);
  }

  public RawAttributes getRaw() {
    return raw;
  }
}
